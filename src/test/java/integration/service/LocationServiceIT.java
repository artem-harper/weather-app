package integration.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.weatherApp.config.TestConfig;
import org.weatherApp.dto.CityInfoDto;
import org.weatherApp.dto.LocationDto;
import org.weatherApp.dto.WeatherInfoDto;
import org.weatherApp.service.LocationService;
import org.weatherApp.service.OpenWeatherAPIService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class LocationServiceIT {

    @MockitoBean
    private OpenWeatherAPIService openWeatherAPIService;

    @Autowired
    private LocationService locationService;

    @Test
    void locationServiceShouldMapJsonNodeToDtoObject() {

        JsonNode apiResponse = createMockJsonResponse();
        Mockito.when(openWeatherAPIService.getAllPossibleCities("Moscow")).thenReturn(apiResponse);

        List<CityInfoDto> list = locationService.findPossibleLocations("Moscow");

        assertThat(list.size()).isEqualTo(2);
    }

    private JsonNode createMockJsonResponse() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.createArrayNode()
                .add(mapper.createObjectNode()
                        .put("name", "Moscow")
                        .put("country", "RU")
                        .put("lat", 55.7558)
                        .put("lon", 37.6173))
                .add(mapper.createObjectNode()
                        .put("name", "Moscow")
                        .put("country", "US")
                        .put("lat", 45.071096)
                        .put("lon", -69.891586));

    }
}
