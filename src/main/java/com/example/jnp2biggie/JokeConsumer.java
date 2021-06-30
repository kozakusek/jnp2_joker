package com.example.jnp2biggie;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JokeConsumer extends RouteBuilder {
    // A route which transfers potentially explicit jokes to telegram conversations
    // using a telegram bot.

    private static final int deliveryTime = 20000;

    @Override
    public void configure() throws Exception {
        var jokeProcessor = new TelegramBotJokeProcessor();

        // Splits jokes into two main types and redirects them.
        from("kafka:{{kafkajoke.topic}}?brokers={{kafka.address}}:{{kafka.port}}")
                .log("Got the joke from the default stream. Broadcasting.")
                .choice()
                    .when(body().contains(Joke.getDeliverySeparator()))
                        .multicast()
                            .to("direct:setup")
                            .to("direct:delivery")
                        .endChoice()
                    .otherwise()
                        .to("direct:joke");

        // Single message jokes.
        from("direct:joke")
                .process(jokeProcessor.getJokeProcessor("{{telegram.jokebot.auth.token}}"));

        // Question-Answer jokes.
        from("direct:setup")
                .process(jokeProcessor.getSetupProcessor("{{telegram.jokebot.auth.token}}"));

        from("direct:delivery")
                .delay(deliveryTime)
                .process(jokeProcessor.getDeliveryProcessor("{{telegram.jokebot.auth.token}}"));

        // Register chats to communicate with.
        from("telegram:bots?authorizationToken={{telegram.jokebot.auth.token}}")
                .log("Bot recieved a message.")
                .process(jokeProcessor);
    }
}
