package chocola.order_service.service;

import chocola.order_service.dto.OrderDto;
import chocola.order_service.entity.OrderEntity;
import chocola.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getQuantity() * orderDto.getUnitPrice());

        OrderEntity orderEntity = mapper.map(orderDto, OrderEntity.class);
        orderRepository.save(orderEntity);

        return orderDto;
    }

    public List<OrderEntity> getOrdersByUserId(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
