FROM openjdk:17.0.1-slim

WORKDIR /spring-cloud

COPY kafka/confluent-7.7.0 ./confluent-7.7.0
COPY kafka/kafka_2.13-3.8.0 ./kafka_2.13-3.8.0
COPY kafka/confluentinc-kafka-connect-jdbc-10.7.6/lib/kafka-connect-jdbc-10.7.6.jar ./java/

EXPOSE 9092
EXPOSE 8083

CMD ["/bin/bash"]