package org.weatherApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.weatherApp.dto.CityInfoDto;
import org.weatherApp.dto.LoginUserDto;
import org.weatherApp.dto.SessionDto;
import org.weatherApp.service.LocationService;
import org.weatherApp.service.SessionService;
import java.time.LocalDateTime;
import java.util.List;


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
        userDto = sessionDto.getUserDto();

        sessionService.isSessionExpired(LocalDateTime.now(), sessionId);

        model.addAttribute("user", userDto);
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@CookieValue(value = "SESSIONID", required = false) String sessionId, @RequestParam("location") String location, Model model) {

        sessionService.isSessionExpired(LocalDateTime.now(), sessionId);

        List<CityInfoDto> cities = locationService.findCities(location);
        model.addAttribute("list", cities);


        return "search-results";
    }

}
