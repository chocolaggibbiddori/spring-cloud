FROM maven AS build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=build /app/target/order-service-1.0.jar order-service-1.0.jar

ENV SPRING_DATASOURCE_URL=jdbc:mariadb://mariadb:3306/spring_cloud
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-service:8761/eureka
ENV MANAGEMENT_ZIPKIN_TRACING_ENDPOINT=http://zipkin:9411/api/v2/spans
ENV LOGGING_FILE=/api-logs/orders-ws.log

ENTRYPOINT ["java", "-jar", "order-service-1.0.jar"]
