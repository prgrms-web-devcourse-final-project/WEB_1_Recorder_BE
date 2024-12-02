FROM openjdk:17-jdk-slim
ARG JAR_FILE=RevUp-api/build/libs/RevUp-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-jar", "/app.jar"]