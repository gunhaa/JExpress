FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y dos2unix \
    && dos2unix ./gradlew \
    && chmod +x ./gradlew

RUN ./gradlew shadowJar

EXPOSE 8020

CMD ["java", "-jar", "/app/build/libs/JExpress-1.0-SNAPSHOT-all.jar"]