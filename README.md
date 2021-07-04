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

### How does it work?
The prodcuer every ``` sendingPeriod ```ms queries the API located at https://v2.jokeapi.dev/.  
After recieving a response, the producer processes it and places in a kafka stream.  
This is where our consumer step in -- they pick the data from individual kafka streams and transfer
it to telegram bots, which broadcast the recieved jokes on all known chats.  
Everything is set up by camel routes and run in docker containers.  
There are two types of jokes:  
 - Question and answer
 - Single message
Q&A jokes have a small delay between the Q and the A.  

There are also two kinds of bots, one which sends any jokes it recieves  
and the second one, which queries for more family friendly humour.

#### Important notice
The bots require an inital message to register chat IDs.
