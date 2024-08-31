FROM maven as build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=build /app/target/apigateway-service-1.0.jar apigateway-service-1.0.jar

ENV SPRING_CLOUD_CONFIG_URI=http://config-service:8888
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-service:8761/eureka

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "apigateway-service-1.0.jar"]
