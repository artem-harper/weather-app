package integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.config.TestConfig;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.dto.UserDto;
import org.weatherApp.exceptions.SessionExpiredException;
import org.weatherApp.service.SessionService;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class SessionServiceIT {

    @Autowired
    private SessionService sessionService;

    @Test
    void expiredSessionShouldThrowException(){
        SessionDto sessionDto = SessionDto.builder()
                .id(UUID.randomUUID())
                .userDto(UserDto.builder()
                        .id(1)
                        .build())
                .build();

        SessionDto sessionDto1 = sessionService.saveSession(sessionDto);

        assertThrows(SessionExpiredException.class, ()->sessionService.isSessionExpired(LocalDateTime.now().plusDays(30), sessionDto1.getId().toString()));
    }
}
