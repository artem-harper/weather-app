package org.weatherApp.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.weatherApp.exceptions.SessionNotFoundException;
import org.weatherApp.service.SessionService;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Controller
public class WeatherSearchController {

    private final SessionService sessionService;

    public WeatherSearchController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping()
    public String mainPage(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies() == null ? new Cookie[]{} : httpServletRequest.getCookies();

        Optional<Cookie> sessionId = Arrays.stream(cookies).filter(cookie -> Objects.equals(cookie.getName(), "SESSIONID")).findFirst();

        try {
            if (sessionId.isEmpty()) {
                return "redirect:/sign-in";
            } else {
                sessionService.findSession(sessionId.get());
            }
        } catch (SessionNotFoundException e) {
            return "redirect:/sign-in";
        }

        return "index";
    }
}
