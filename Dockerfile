FROM openjdk:17-jdk-slim

# curl과 필요한 패키지 설치
RUN apt-get update && apt-get install -y curl
# dockerize 설치
RUN curl -sSL https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz | tar -xzC /usr/local/bin

ARG JAR_FILE=RevUp-api/build/libs/RevUp-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ "dockerize", "-wait", "tcp://mysql:3306", "-timeout", "30s", "java", "-Dspring.profiles.active=dev", "-Dhttps.protocols=TLSv1.2,TLSv1.3", "-jar", "/app.jar" ]
