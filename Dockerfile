FROM openjdk:17-jdk-slim
ARG JAR_FILE=RevUp-api/build/libs/RevUp-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 필요한 패키지 먼저 설치
RUN apt-get update && apt-get install -y \
    gcc \
    libmysqlclient-dev \
    mysql-client

# wait-for-it.sh 스크립트를 복사
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["java","-Dspring.profiles.active=local", "-jar", "/app.jar"]