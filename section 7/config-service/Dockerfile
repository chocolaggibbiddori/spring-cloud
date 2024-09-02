FROM maven as build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1-jdk-slim

RUN apt-get update && apt-get install -y curl

WORKDIR /app
COPY apiEncryptionKey.jks .
COPY --from=build /app/target/config-service-1.0.jar config-service-1.0.jar

ENV ENCRYPT_KEY_STORE_LOCATION=file:/app/apiEncryptionKey.jks
ENV SPRING_PROFILES_ACTIVE=git

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config-service-1.0.jar"]
