package org.weatherApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.dto.CityInfoDto;
import org.weatherApp.dto.LocationDto;
import org.weatherApp.dto.WeatherInfoDto;
import org.weatherApp.entity.Location;
import org.weatherApp.exceptions.LocationAlreadyExistException;
import org.weatherApp.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final OpenWeatherService openWeatherService;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public LocationService(LocationRepository locationRepository, OpenWeatherService openWeatherService, ObjectMapper objectMapper, ModelMapper modelMapper, UserService userService) {
        this.locationRepository = locationRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.openWeatherService = openWeatherService;
        this.userService = userService;
    }

    public List<CityInfoDto> findPossibleLocations(String city) {

        List<CityInfoDto> cityInfoDtoList = new ArrayList<>();

        JsonNode allPossibleCities = openWeatherService.getAllPossibleCities(city);

        for (JsonNode element : allPossibleCities) {
            try {
                cityInfoDtoList.add(objectMapper.readValue(element.toString(), CityInfoDto.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return cityInfoDtoList;
    }

    public List<WeatherInfoDto> findWeatherForLocations(List<LocationDto> locationDtoList) {
        List<WeatherInfoDto> weathers = new ArrayList<>();

        for (LocationDto locationDto : locationDtoList) {
            JsonNode weatherForCity = openWeatherService.getWeatherForCity(locationDto.getLatitude(), locationDto.getLongitude());

            WeatherInfoDto weatherInfoDto = WeatherInfoDto.builder()
                    .id(locationDto.getId())
                    .temp(weatherForCity.get("main").get("temp").intValue())
                    .name(locationDto.getName())
                    .country(weatherForCity.get("sys").get("country").asText())
                    .feelsLike(weatherForCity.get("main").get("feels_like").intValue())
                    .description(weatherForCity.get("weather").get(0).get("description").asText())
                    .humidity(weatherForCity.get("main").get("humidity").asText())
                    .build();

            weathers.add(weatherInfoDto);

        }

        return weathers;
    }

    @Transactional
    public LocationDto saveLocation(LocationDto locationDto) {

        List<LocationDto> locationDtoList = locationDto.getUserId().getLocationDtoList();

        if (locationDtoList.stream().anyMatch(locationDto1 -> Objects.equals(locationDto1.getLongitude(), locationDto.getLongitude()) && Objects.equals(locationDto1.getLatitude(), locationDto.getLatitude()))){
            throw new LocationAlreadyExistException();
        }

        Location savedLocation= locationRepository.save(modelMapper.map(locationDto, Location.class));
        return modelMapper.map(savedLocation, LocationDto.class);
    }

    @Transactional
    public void deleteLocation(Integer id) {
        locationRepository.delete(id);
    }
}
