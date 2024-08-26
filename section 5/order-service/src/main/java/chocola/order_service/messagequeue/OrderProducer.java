package chocola.order_service.messagequeue;

import chocola.order_service.dto.OrderDto;
import chocola.order_service.messagequeue.dto.Field;
import chocola.order_service.messagequeue.dto.KafkaOrderDto;
import chocola.order_service.messagequeue.dto.Payload;
import chocola.order_service.messagequeue.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    private static final List<Field> FIELDS = Arrays.asList(
            new Field("string", true, "order_id"),
            new Field("string", true, "user_id"),
            new Field("string", true, "product_id"),
            new Field("int32", true, "quantity"),
            new Field("int32", true, "unit_price"),
            new Field("int32", true, "total_price")
    );
    private static final Schema SCHEMA = Schema.builder()
            .type("struct")
            .fields(FIELDS)
            .optional(false)
            .name("orders")
            .build();

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, OrderDto orderDto) {
        Payload payload = Payload.builder()
                .orderId(orderDto.getOrderId())
                .userId(orderDto.getUserId())
                .productId(orderDto.getProductId())
                .quantity(orderDto.getQuantity())
                .unitPrice(orderDto.getUnitPrice())
                .totalPrice(orderDto.getTotalPrice())
                .build();

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(SCHEMA, payload);

        ObjectMapper om = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = om.writeValueAsString(kafkaOrderDto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Order Producer sent data from the Order microservice: {}", kafkaOrderDto);
    }
}
