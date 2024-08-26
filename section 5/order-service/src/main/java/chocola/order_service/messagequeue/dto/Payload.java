package chocola.order_service.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Payload {

    private String order_id;
    private String user_id;
    private String product_id;
    private int quantity;
    private int unit_price;
    private int total_price;

    @Builder
    public Payload(String orderId, String userId, String productId, int quantity, int unitPrice, int totalPrice) {
        this.order_id = orderId;
        this.user_id = userId;
        this.product_id = productId;
        this.quantity = quantity;
        this.unit_price = unitPrice;
        this.total_price = totalPrice;
    }
}
