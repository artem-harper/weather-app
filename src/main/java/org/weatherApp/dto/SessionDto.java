package org.weatherApp.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {

    private UUID id;
    private UserDto userDto;
    private LocalDateTime expiresAt;
}
