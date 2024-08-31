FROM maven as build

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17.0.1-jdk-slim

WORKDIR /app
COPY --from=build /app/target/discoveryservice-1.0.jar discoveryservice-1.0.jar

ENV SPRING_CLOUD_CONFIG_URI=http://config-service:8888

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "discoveryservice-1.0.jar"]
