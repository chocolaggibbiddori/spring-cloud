package chocola.catalog_service.messagequeue;

import chocola.catalog_service.entity.CatalogEntity;
import chocola.catalog_service.repository.CatalogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CatalogRepository catalogRepository;

    @Transactional
    @KafkaListener(topics = "catalog-topic")
    public void updateQuantity(String kafkaMessage) {
        log.info("Kafka Message: {}", kafkaMessage);

        Map<String, Object> map = new HashMap<>();
        ObjectMapper om = new ObjectMapper();
        try {
            map = om.readValue(kafkaMessage, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        Optional<CatalogEntity> entityOpt = catalogRepository.findByProductId((String) map.get("productId"));

        int quantity = (int) map.get("quantity");
        entityOpt.ifPresent(entity -> entity.reduceStock(quantity));
    }
}
