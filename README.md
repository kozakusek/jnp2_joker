# Telegram Definitely Funny Bot
A simple app made for jnp2 classes at MIMUW.  
Its main goal is to demonstrate basic usage of docker, kafka streams and apache-camel.

### Usage
Just get your telegram bot authentication tokens, place them in
```
src/main/resources/application.properties
```
set the timers,
```
docker-compose up
```
and enjoy two versions of a joke-sending bot :)

#### Important notice
The bots require an inital message to register chat IDs.
