package chocola.user_service.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> null;
            case 404 -> {
                if (methodKey.contains("getOrders")) yield new ResponseStatusException(HttpStatus.NOT_FOUND);
                yield null;
            }
            default -> new Exception(response.reason());
        };
    }
}
