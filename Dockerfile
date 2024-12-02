FROM openjdk:17-jdk-slim

# JAR 파일 복사
ARG JAR_FILE=RevUp-api/build/libs/RevUp-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar

# Spring Boot 실행
ENTRYPOINT ["java", "-Dspring.config.location=/app/config/", "-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]
