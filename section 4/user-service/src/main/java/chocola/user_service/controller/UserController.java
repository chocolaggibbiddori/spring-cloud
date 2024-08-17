package chocola.user_service.controller;

import chocola.user_service.dto.UserDto;
import chocola.user_service.service.UserService;
import chocola.user_service.vo.RequestUser;
import chocola.user_service.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {

    private final UserService userService;
    private final Environment env;

    @Value("${greeting.message}")
    private String greetingMessage;

    @GetMapping("/health_check")
    public String status() {
        return "It's working in User-Service on PORT %s".formatted(env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return this.greetingMessage;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    public ResponseUser createUser(@RequestBody RequestUser user) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());

        userService.createUser(userDto);

        return mapper.map(userDto, ResponseUser.class);
    }
}
