package chocola.user_service.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestLogin {

    @NotNull(message = "Email can't be null")
    @Size(min = 2, message = "Email not be less than 2 characters")
    @Email
    private String email;

    @NotNull(message = "Password can't be null")
    @Size(min = 8, message = "Password must be equals or greater than 8 characters")
    private String password;
}
