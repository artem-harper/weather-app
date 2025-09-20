package org.weatherApp;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.weatherApp.dto.CityInfoDto;
import org.weatherApp.service.OpenWeatherService;

import java.util.List;


public class Main{
    public static void main(String[] args) throws JsonProcessingException {
        OpenWeatherService openWeatherService = new OpenWeatherService();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode cities = openWeatherService.getAllPossibleCities("Moscow");

        for (JsonNode element: cities){
            objectMapper.readValue(element.toString(), CityInfoDto.class);
            System.out.println();
        }
    }
}
