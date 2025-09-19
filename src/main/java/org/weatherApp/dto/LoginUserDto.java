package org.weatherApp.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    private Integer id;
    private String login;
    private String password;

}
