#!/bin/bash
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
docker run -it -p 8080:8080 -v $DIR:/jnp2 jnp2biggie_jnp2biggie:latest
