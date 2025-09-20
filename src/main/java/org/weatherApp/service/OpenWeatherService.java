package org.weatherApp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class OpenWeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_KEY = "7af4ea41ee5df1d943898cbd59555701";


    public JsonNode getAllPossibleCities(String city) {
        int citiesLimit = 5;
        String request = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%s&appid=%s";

        return restTemplate.getForObject(request.formatted(city, citiesLimit, API_KEY), JsonNode.class);
    }

    public List<JsonNode> getWeatherForCity(String city) {
        String request = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";
        JsonNode jsonNode = getAllPossibleCities(city);
        List<JsonNode> cities = new ArrayList<>();

        for (JsonNode element : jsonNode) {
            cities.add(restTemplate.getForObject(request.formatted(element.get("lat"), element.get("lon"), API_KEY), JsonNode.class));
        }

        return cities;
    }

}
