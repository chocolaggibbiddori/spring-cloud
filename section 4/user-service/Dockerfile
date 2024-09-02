FROM maven as build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=build /app/target/user-service-1.0.jar user-service-1.0.jar

ENV SPRING_CLOUD_CONFIG_URI=http://config-service:8888
ENV SPRING_RABBITMQ_HOST=rabbitmq
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://eureka-service:8761/eureka
ENV MANAGEMENT_ZIPKIN_TRACING_ENDPOINT=http://zipkin:9411/api/v2/spans
ENV ORDER_SERVICE_URL=http://order-service/%s/orders
ENV LOGGING_FILE=/api-logs/users-ws.log

ENTRYPOINT ["java", "-jar", "user-service-1.0.jar"]
