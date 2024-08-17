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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service")
public class UserController {

    private final UserService userService;
    private final Environment env;
    private final ModelMapper mapper = new ModelMapper();

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

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseUser createUser(@RequestBody RequestUser user) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(user, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());

        userService.createUser(userDto);

        return mapper.map(userDto, ResponseUser.class);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseUser> getUsers() {
        return userService.getAllUser().stream()
                .map(ue -> mapper.map(ue, ResponseUser.class))
                .toList();
    }

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseUser getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        return mapper.map(userDto, ResponseUser.class);
    }
}
