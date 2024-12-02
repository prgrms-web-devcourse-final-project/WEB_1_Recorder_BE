FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y curl \
    && curl -sSLo /usr/local/bin/dockerize https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz \
    && tar -C /usr/local/bin -xvzf /usr/local/bin/dockerize-linux-amd64-v0.6.1.tar.gz \
    && chmod +x /usr/local/bin/dockerize

ARG JAR_FILE=RevUp-api/build/libs/RevUp-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "dockerize", "-wait", "tcp://mysql:3306", "-timeout", "30s", "java", "-Dspring.profiles.active=local", "-jar", "/app.jar" ]
