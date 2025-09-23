package integration.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.config.TestConfig;
import org.weatherApp.exceptions.SessionExpiredException;
import org.weatherApp.repository.SessionRepository;
import org.weatherApp.service.SessionService;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class SessionServiceIT {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;


    @Test
    void shouldDecrementSessionsCountWithDeletedSession() {

        int sessionsCountBeforeDeleting = sessionRepository.findAll().size();

        sessionService.deleteSession("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

        int sessionsCountAfterDeleting = sessionRepository.findAll().size();

        assertThat(sessionsCountBeforeDeleting-sessionsCountAfterDeleting).isEqualTo(1);
    }

    @Test
    void expiredSessionShouldThrowException() {
        assertThrows(SessionExpiredException.class, () -> sessionService.isSessionExpired(LocalDateTime.now(), "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11"));
    }
}
