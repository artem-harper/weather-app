package org.weatherApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.weatherApp.dto.CityInfoDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private final OpenWeatherService openWeatherService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LocationService(OpenWeatherService openWeatherService) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.openWeatherService = openWeatherService;
    }

    public List<CityInfoDto> findCities(String city) {

        List<CityInfoDto> cityInfoDtoList = new ArrayList<>();

        JsonNode allPossibleCities = openWeatherService.getAllPossibleCities(city);

        for (JsonNode element: allPossibleCities){
            try {
                cityInfoDtoList.add(objectMapper.readValue(element.toString(), CityInfoDto.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return cityInfoDtoList;
    }

}
