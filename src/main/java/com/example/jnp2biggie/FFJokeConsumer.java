package com.example.jnp2biggie;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FFJokeConsumer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:{{kafkajoke.topic.safe}}?brokers={{kafka.address}}:{{kafka.port}}")
                .log("Got the joke from the safe stream:\n${body}");
    }
}
