package org.weatherApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@PropertySource("classpath:application.properties")
public class OpenWeatherAPIService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Environment environment;

    public OpenWeatherAPIService(Environment environment) {
        this.environment = environment;
    }

    public JsonNode getAllPossibleCities(String city) {
        int citiesLimit = 5;
        String request = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%s&appid=%s";

        return restTemplate.getForObject(request.formatted(city, citiesLimit, environment.getRequiredProperty("api.key")), JsonNode.class);
    }

    public JsonNode getWeatherForCity(BigDecimal latitude, BigDecimal longitude) {
        String request = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";

        return restTemplate.getForObject(request.formatted(latitude, longitude, environment.getRequiredProperty("api.key")), JsonNode.class);
    }
}
