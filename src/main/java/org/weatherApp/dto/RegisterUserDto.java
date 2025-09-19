package org.weatherApp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    @NotEmpty(message = "Login shouldn't be empty")
    @Size(min=4, max = 20, message = "Username should be between 4 and 20 characters")
    private String login;

    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min=4, max = 20, message = "Password should be between 4 and 20 characters")
    private String password;

    @NotEmpty(message = "Please repeat your password")
    private String repeatPassword;
}
