package chocola.order_service.controller;

import chocola.order_service.dto.OrderDto;
import chocola.order_service.messagequeue.KafkaProducer;
import chocola.order_service.messagequeue.OrderProducer;
import chocola.order_service.service.OrderService;
import chocola.order_service.vo.RequestOrder;
import chocola.order_service.vo.ResponseOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;
    private final ModelMapper mapper;

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return "It's working in Order-Service on PORT %s".formatted(request.getLocalPort());
    }

    @PostMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOrder createOrder(@RequestBody RequestOrder order, @PathVariable("userId") String userId) {
        log.info("Before add orders data");
        OrderDto orderDto = mapper.map(order, OrderDto.class);
        orderDto.setUserId(userId);
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(order.getQuantity() * order.getUnitPrice());

        OrderDto createdOrderDto = orderService.createOrder(orderDto);
//        kafkaProducer.send("catalog-topic", orderDto);
//        orderProducer.send("orders", orderDto);

        log.info("After added orders data");
        return mapper.map(createdOrderDto, ResponseOrder.class);
    }

    @GetMapping("/{userId}/orders")
    public List<ResponseOrder> getOrders(@PathVariable("userId") String userId) {
        log.info("Before retrieve orders data");
        List<ResponseOrder> responseOrderList = orderService.getOrdersByUserId(userId).stream()
                .map(entity -> mapper.map(entity, ResponseOrder.class))
                .toList();
        log.info("After retrieved orders data");

        return responseOrderList;
    }
}
