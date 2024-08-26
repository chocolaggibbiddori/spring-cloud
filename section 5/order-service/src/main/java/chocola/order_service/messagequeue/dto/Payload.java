package chocola.order_service.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Payload {

    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private int unitPrice;
    private int totalPrice;

    @Builder
    public Payload(String orderId, String userId, String productId, int quantity, int unitPrice, int totalPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
