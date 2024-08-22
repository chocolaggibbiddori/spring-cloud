package chocola.user_service.client;

import chocola.user_service.vo.ResponseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("order-service")
public interface OrderServiceClient {

    @GetMapping("/{userId}/orders")
    List<ResponseOrder> getOrders(@PathVariable("userId") String userId);
}
