package chocola.user_service.service;

import chocola.user_service.dto.UserDto;
import chocola.user_service.entity.UserEntity;
import chocola.user_service.repository.UserRepository;
import chocola.user_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final Environment env;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userRepository.save(userEntity);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow();
        UserDto userDto = mapper.map(userEntity, UserDto.class);

        String orderServiceUrl = Objects.requireNonNull(env.getProperty("order_service.url")).formatted(userDto.getUserId());

        ResponseEntity<List<ResponseOrder>> responseEntity =
                restTemplate.exchange(
                        orderServiceUrl, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        List<ResponseOrder> orderList = responseEntity.getBody();
        userDto.setOrders(orderList);

        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow();
        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username).orElseThrow();
        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(), new ArrayList<>());
    }
}
