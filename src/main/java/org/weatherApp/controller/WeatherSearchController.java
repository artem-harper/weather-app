package org.weatherApp.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.exceptions.SessionExpiredException;
import org.weatherApp.exceptions.SessionNotFoundException;
import org.weatherApp.service.SessionService;

import java.time.LocalDateTime;


@Controller
public class WeatherSearchController {

    private final SessionService sessionService;

    public WeatherSearchController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping()
    public String mainPage(@CookieValue(value = "SESSIONID", required = false) String sessionId, Model model) {

        LoginUserDto userDto;

        if (sessionId == null){
            return "redirect:/sign-in";
        }

        try {
            SessionDto sessionDto = sessionService.findSession(sessionId);
            sessionService.isSessionExpired(LocalDateTime.now(), sessionId);
            userDto = sessionDto.getUserDto();
        }catch (SessionNotFoundException | SessionExpiredException e){
            return "redirect:/sign-in";
        }

        model.addAttribute("user", userDto);
        return "index";
    }
}
