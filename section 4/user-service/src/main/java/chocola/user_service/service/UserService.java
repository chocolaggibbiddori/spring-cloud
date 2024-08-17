package chocola.user_service.service;

import chocola.user_service.dto.UserDto;
import chocola.user_service.entity.UserEntity;

import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    List<UserEntity> getAllUser();
}
