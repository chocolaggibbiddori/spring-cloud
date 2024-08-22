package chocola.order_service.controller;

import chocola.order_service.dto.OrderDto;
import chocola.order_service.service.OrderService;
import chocola.order_service.vo.RequestOrder;
import chocola.order_service.vo.ResponseOrder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper mapper;

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return "It's working in Order-Service on PORT %s".formatted(request.getLocalPort());
    }

    @PostMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOrder createOrder(@RequestBody RequestOrder order, @PathVariable("userId") String userId) {
        OrderDto orderDto = mapper.map(order, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto createdOrderDto = orderService.createOrder(orderDto);
        return mapper.map(createdOrderDto, ResponseOrder.class);
    }

    @GetMapping("/{userId}/orders")
    public List<ResponseOrder> getOrders(@PathVariable("userId") String userId) {
        return orderService.getOrdersByUserId(userId).stream()
                .map(entity -> mapper.map(entity, ResponseOrder.class))
                .toList();
    }
}
