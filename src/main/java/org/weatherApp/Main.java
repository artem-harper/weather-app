package org.weatherApp;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import org.weatherApp.service.OpenWeatherService;

import java.util.List;


public class Main{
    public static void main(String[] args) throws JsonProcessingException {
        OpenWeatherService openWeatherService = new OpenWeatherService();

        List<JsonNode> moscow = openWeatherService.getWeatherForCity("пангоды");

        System.out.println(moscow);
    }
}
