package org.weatherApp.service;


import jakarta.servlet.http.Cookie;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.entity.Session;
import org.weatherApp.entity.User;
import org.weatherApp.exceptions.SessionExpiredException;
import org.weatherApp.exceptions.SessionNotFoundException;
import org.weatherApp.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SessionService {

    private final int SESSION_LIFETIME_MIN = 30;
    private final SessionRepository sessionRepository;
    private final ModelMapper modelMapper;

    public SessionService(SessionRepository sessionRepository, ModelMapper modelMapper) {
        this.sessionRepository = sessionRepository;
        this.modelMapper = modelMapper;
    }

    public SessionDto saveSession(SessionDto sessionDto){
        sessionDto.setExpiresAt(LocalDateTime.now().plusMinutes(SESSION_LIFETIME_MIN));

        Session session = modelMapper.map(sessionDto, Session.class);

        return modelMapper.map(sessionRepository.save(session), SessionDto.class);
    }

    public SessionDto findSession(String sessionId) {
        return sessionRepository.findById(UUID.fromString(sessionId))
                .map(session -> modelMapper.map(session, SessionDto.class)).orElseThrow(SessionNotFoundException::new);
    }

    public void isSessionExpired(LocalDateTime now, String sessionId){

        LocalDateTime expiresAt = findSession(sessionId).getExpiresAt();

        if (now.isAfter(expiresAt)){
            throw new SessionExpiredException();
        }
    }
}
