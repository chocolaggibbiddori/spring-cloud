package chocola.user_service.service;

import chocola.user_service.dto.UserDto;
import chocola.user_service.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    List<UserEntity> getAllUser();
}
