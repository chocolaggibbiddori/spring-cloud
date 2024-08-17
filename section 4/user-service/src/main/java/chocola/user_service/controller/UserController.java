package chocola.user_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/health_check")
    public String status() {
        return "It's working in User-Service";
    }
}
