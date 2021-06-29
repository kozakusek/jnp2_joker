package com.example.jnp2biggie;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JokeConsumer extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:{{kafkajoke.topic}}?brokers={{kafka.address}}:{{kafka.port}}")
                .log("Got the joke from the default stream:\n${body}");
    }
}
