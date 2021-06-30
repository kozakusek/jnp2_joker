package com.example.jnp2biggie;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.component.jackson.JacksonDataFormat;

@Component
public class JokeProducer extends RouteBuilder {
    // A route which, every api.sendingPeriod ms, sends
    // two types of jokes (potentially explicit and family friendly)
    // to kafka streams.

    @Override
    public void configure() throws Exception {
        var format = new JacksonDataFormat(Joke.class);

        from("timer://myTimer?fixedRate=true&period={{api.sendingPeriod}}")
                .to("direct:start")
                .to("direct:safe-start");

        from("direct:start")
                .to("https:{{jokeapi.address}}")
                .unmarshal(format)
                .to("kafka:{{kafkajoke.topic}}?brokers={{kafka.address}}:{{kafka.port}}")
                .log("Sent a potentialy explicit joke\n");

        from("direct:safe-start")
                .to("https:{{jokeapi.address}}{{jokeapi.blacklist}}")
                .unmarshal(format)
                .to("kafka:{{kafkajoke.topic.safe}}?brokers={{kafka.address}}:{{kafka.port}}")
                .log("Sent a family friendly joke\n");
    }
}
