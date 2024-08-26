package chocola.order_service.messagequeue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {

    private String type;
    private boolean optional;
    private String field;
}
