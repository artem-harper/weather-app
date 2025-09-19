package org.weatherApp.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.exceptions.SessionNotFoundException;
import org.weatherApp.service.SessionService;


@Controller
public class WeatherSearchController {

    private final SessionService sessionService;

    public WeatherSearchController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping()
    public String mainPage(@CookieValue(value = "SESSIONID", required = false) String sessionid, Model model) {



        model.addAttribute("user", null);
        return "index";
    }
}
