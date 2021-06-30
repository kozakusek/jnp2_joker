package com.example.jnp2biggie;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FFJokeConsumer extends RouteBuilder {
    // A route which transfers family friendly jokes to telegram conversations
    // using a telegram bot.

    private static final int deliveryTime = 20000;

    @Override
    public void configure() throws Exception {
        var jokeProcessor = new TelegramBotJokeProcessor();

        // Splits jokes into two main types and redirects them.
        from("kafka:{{kafkajoke.topic.safe}}?brokers={{kafka.address}}:{{kafka.port}}")
                .log("Got the joke from the safe stream. Broadcasting.")
                .choice()
                    .when(body().contains(Joke.getDeliverySeparator()))
                        .multicast()
                            .to("direct:setup-safe")
                            .to("direct:delivery-safe")
                        .endChoice()
                    .otherwise()
                        .to("direct:joke-safe");

        // Single message jokes.
        from("direct:joke-safe")
                .process(jokeProcessor.getJokeProcessor("{{telegram.jokebot-safe.auth.token}}"));

        // Question-Answer jokes.
        from("direct:setup-safe")
                .process(jokeProcessor.getSetupProcessor("{{telegram.jokebot-safe.auth.token}}"));

        from("direct:delivery-safe")
                .delay(deliveryTime)
                .process(jokeProcessor.getDeliveryProcessor("{{telegram.jokebot-safe.auth.token}}"));

        // Register chats to communicate with.
        from("telegram:bots?authorizationToken={{telegram.jokebot-safe.auth.token}}")
                .log("Safe JokeBot recieved a message\n")
                .process(jokeProcessor);
    }
}
