FROM openjdk:17-jdk-slim

# 설정 파일을 컨테이너 내의 특정 디렉터리로 복사
COPY RevUp-api/src/main/resources/application.yml /app/config/application.yml
COPY RevUp-common/src/main/resources/application-common.yml /app/config/application-common.yml
COPY RevUp-domain/src/main/resources/application-domain.yml /app/config/application-domain.yml
COPY RevUp-infra/src/main/resources/application-infra.yaml /app/config/application-infra.yaml
COPY RevUp-security/src/main/resources/application-security.yaml /app/config/application-security.yaml

# JAR 파일 복사
ARG JAR_FILE=RevUp-api/build/libs/RevUp-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/app.jar

# Spring Boot 실행
ENTRYPOINT ["java", "-Dspring.config.location=/app/config/", "-Dspring.profiles.active=dev", "-jar", "/app/app.jar"]
