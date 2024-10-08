package chocola.user_service.security.filter;

import chocola.user_service.dto.UserDto;
import chocola.user_service.service.UserService;
import chocola.user_service.vo.RequestLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper mapper = new ObjectMapper();
    private final UserService userService;
    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin requestLogin = mapper.readValue(request.getReader(), RequestLogin.class);
            String email = requestLogin.getEmail();
            String password = requestLogin.getPassword();

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String email = ((User) authResult.getPrincipal()).getUsername();
        UserDto userDto = userService.getUserByEmail(email);

        String token = Jwts.builder()
                .subject(userDto.getUserId())
                .expiration(getExpirationDate())
                .signWith(getKey())
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDto.getUserId());
    }

    private Date getExpirationDate() {
        String exp = Objects.requireNonNull(env.getProperty("token.expiration_time"));
        return new Date(System.currentTimeMillis() + Long.parseLong(exp));
    }

    private Key getKey() {
        String secretKey = Objects.requireNonNull(env.getProperty("token.secret"));
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
