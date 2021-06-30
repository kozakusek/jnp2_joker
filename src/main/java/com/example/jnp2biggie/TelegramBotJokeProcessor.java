package com.example.jnp2biggie;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;

import java.util.*;

public class TelegramBotJokeProcessor implements org.apache.camel.Processor {
    // A special class that processes jokes for telegram joke api.
    // Receives messages from kafka streams and sends received jokes
    // to telegram bot endpoints.
    // Keeps a HashSet of chat ID's of all connected telegram chats.

    private HashSet<String> chatIds;
    private static final String chatIdPtr = "chat=Chat{id='";
    private static final String chatIdEndptr = "', title=";

    public TelegramBotJokeProcessor() {
        this.chatIds = new HashSet<String>();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        chatIds.add(String.valueOf(exchange.getIn().getHeader("CamelTelegramChatId")));
    }

    public Collection<String> getChatIds() {
        return chatIds;
    }

    // Processor for question part in Q-A jokes.
    public Processor getSetupProcessor(String authToken) {
        return new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                try {
                    String setup = exchange.getIn().getBody(String.class)
                            .split(Joke.getDeliverySeparator())[0];
                    exchange.getIn().setBody(setup);

                    ProducerTemplate template = exchange.getContext().createProducerTemplate();
                    for (var id : getChatIds()) {
                        template.sendBody("telegram:bots" +
                                "?authorizationToken=" + authToken +
                                "&chatId=" + id, setup);
                    }
                } catch (Exception ignored) {}
            }
        };
    }

    // Processor for answer part in Q-A jokes.
    public Processor getDeliveryProcessor(String authToken) {
        return new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                try {
                    String delivery = exchange.getIn().getBody(String.class)
                            .split(Joke.getDeliverySeparator())[1];
                    exchange.getIn().setBody(delivery);

                    ProducerTemplate template = exchange.getContext().createProducerTemplate();
                    for (var id : getChatIds()) {
                        template.sendBody("telegram:bots" +
                                "?authorizationToken=" + authToken +
                                "&chatId=" + id, delivery);
                    }
                } catch (Exception ignored) {}
            }
        };
    }

    // Processor for single message jokes.
    public Processor getJokeProcessor(String authToken) {
        return new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                try {
                    String joke = exchange.getIn().getBody(String.class);
                    exchange.getIn().setBody(joke);

                    ProducerTemplate template = exchange.getContext().createProducerTemplate();
                    for (var id : getChatIds()) {
                        template.sendBody("telegram:bots" +
                                "?authorizationToken=" + authToken +
                                "&chatId=" + id, joke);
                    }
                } catch (Exception ignored) {}
            }
        };
    }
}
