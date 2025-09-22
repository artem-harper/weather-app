package org.weatherApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;

@Service
public class OpenWeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_KEY = "7af4ea41ee5df1d943898cbd59555701";


    public JsonNode getAllPossibleCities(String city) {
        int citiesLimit = 5;
        String request = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%s&appid=%s";

        return restTemplate.getForObject(request.formatted(city, citiesLimit, API_KEY), JsonNode.class);
    }

   public JsonNode getWeatherForCity(BigDecimal latitude, BigDecimal longitude) {
        String request = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric";

        return restTemplate.getForObject(request.formatted(latitude, longitude, API_KEY), JsonNode.class);
    }
}
