package org.weatherApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.weatherApp.dto.*;
import org.weatherApp.service.LocationService;
import org.weatherApp.service.SessionService;
import org.weatherApp.util.WeatherCodeMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


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
        if (sessionId == null) {
            return "redirect:/sign-in";
        }

        UserDto userDto = sessionService.findSession(sessionId).getUserDto();
        sessionService.isSessionExpired(LocalDateTime.now(), sessionId);

        List<WeatherInfoDto> weatherForLocations = locationService.findWeatherForLocations(userDto.getLocationDtoList());

        model.addAttribute("user", userDto);
        model.addAttribute("weatherList", weatherForLocations);
        model.addAttribute("weatherCodeMap", WeatherCodeMap.getWeatherCodes());
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@CookieValue(value = "SESSIONID", required = false) String sessionId,
                             @RequestParam(value = "location", required = false) String location,
                             @RequestParam(value = "error", required = false) String error, Model model) {

        if (sessionId == null) {
            return "redirect:/sign-in";
        }

        UserDto userDto = sessionService.findSession(sessionId).getUserDto();
        sessionService.isSessionExpired(LocalDateTime.now(), sessionId);

        if (location!=null){
            List<CityInfoDto> cities = locationService.findPossibleLocations(location);
            model.addAttribute("list", cities);
        }

        model.addAttribute("user", userDto);

        if (error != null) {
            model.addAttribute("error", "1");
        }

        return "search-results";
    }


    @PostMapping("/add")
    private String addLocation(@RequestParam("name") String name,
                               @RequestParam("latitude") String latitude,
                               @RequestParam("longitude") String longitude,
                               @CookieValue(value = "SESSIONID", required = false) String sessionId) {

        if (sessionId == null) {
            return "redirect:/sign-in";
        }

        UserDto userDto = sessionService.findSession(sessionId).getUserDto();
        sessionService.isSessionExpired(LocalDateTime.now(), sessionId);

        LocationDto locationDto = LocationDto.builder()
                .name(name)
                .userId(userDto)
                .latitude(new BigDecimal(latitude))
                .longitude(new BigDecimal(longitude))
                .build();

        locationService.saveLocation(locationDto);

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteWeather(@CookieValue(value = "SESSIONID", required = false) String sessionId,
                              @RequestParam("id") Integer id){

        locationService.deleteLocation(id);

        return "redirect:/";
    }

}
