FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y dos2unix \
    && dos2unix ./gradlew \
    && chmod +x ./gradlew

RUN ./gradlew shadowJar

ENV PORT=${PORT}

CMD ["java", "-jar", "/app/build/libs/JExpress-1.0-SNAPSHOT-all.jar"]