package org.weatherApp.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class WeatherSearchController {

    @GetMapping()
    public String mainPage(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies() == null ? new Cookie[]{} : httpServletRequest.getCookies();
        Cookie sessionID = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("sessionId")).findAny().orElse(null);
        if (sessionID == null) {
            return "redirect:/sign-in";
        }

        return "index";
    }
}
