FROM maven AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=build /app/target/catalog-service-1.0.jar catalog-service-1.0.jar

ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-service:8761/eureka
ENV LOGGING_FILE=/api-logs/catalogs-ws.log

ENTRYPOINT ["java", "-jar", "catalog-service-1.0.jar"]
