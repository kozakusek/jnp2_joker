FROM openjdk:latest
WORKDIR /jnp2
CMD ["java","-jar","build/libs/jnp2biggie-0.0.1-SNAPSHOT.jar"]
