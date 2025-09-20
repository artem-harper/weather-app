package org.weatherApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.exceptions.SessionExpiredException;
import org.weatherApp.exceptions.SessionNotFoundException;
import org.weatherApp.service.LocationService;
import org.weatherApp.service.SessionService;

import javax.xml.stream.Location;
import java.time.LocalDateTime;


@Controller
public class WeatherSearchController {

    private final SessionService sessionService;
    private final LocationService locationService;

    public WeatherSearchController(SessionService sessionService, LocationService locationService) {
        this.sessionService = sessionService;
        this.locationService = locationService;
    }

    @GetMapping()
    public String mainPage(@CookieValue(value = "SESSIONID", required = false) String sessionId, Model model) {

        LoginUserDto userDto;

        if (sessionId == null) {
            return "redirect:/sign-in";
        }

        SessionDto sessionDto = sessionService.findSession(sessionId);
        sessionService.isSessionExpired(LocalDateTime.now(), sessionId);
        userDto = sessionDto.getUserDto();

        model.addAttribute("user", userDto);
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam("location") String location) {

        return "search-results";
    }

}
