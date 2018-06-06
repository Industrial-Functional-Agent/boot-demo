FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY entrypoint.sh ./entrypoint.sh
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["/bin/sh", "./entrypoint.sh"]
