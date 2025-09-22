package org.weatherApp.service;


import jakarta.servlet.http.Cookie;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.dto.LocationDto;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.entity.Location;
import org.weatherApp.entity.Session;
import org.weatherApp.entity.User;
import org.weatherApp.exceptions.SessionExpiredException;
import org.weatherApp.exceptions.SessionNotFoundException;
import org.weatherApp.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional
    public SessionDto saveSession(SessionDto sessionDto){
        sessionDto.setExpiresAt(LocalDateTime.now().plusMinutes(SESSION_LIFETIME_MIN));

        Session session = modelMapper.map(sessionDto, Session.class);

        return modelMapper.map(sessionRepository.save(session), SessionDto.class);
    }

    @Transactional
    public SessionDto findSession(String sessionId) {
        Session session = sessionRepository.findById(UUID.fromString(sessionId)).orElseThrow(SessionNotFoundException::new);

        SessionDto sessionDto = modelMapper.map(session, SessionDto.class);

        sessionDto.getUserDto().setLocationDtoList(session.getUser().getLocations().stream()
                .map(location -> modelMapper.map(location, LocationDto.class))
                .toList());

        return sessionDto;
    }

    @Transactional
    public void isSessionExpired(LocalDateTime now, String sessionId){

        LocalDateTime expiresAt = findSession(sessionId).getExpiresAt();

        if (now.isAfter(expiresAt)){
            throw new SessionExpiredException();
        }
    }

    @Transactional
    public void deleteSession(String sessionId){
        sessionRepository.delete(UUID.fromString(sessionId));

    }
}
