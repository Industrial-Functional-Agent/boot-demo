#!/bin/sh

if [ -n "$(which java)" ]; then
    java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
fi
