package org.weatherApp.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfoDto {
    private Integer id;
    private Integer temp;
    private String name;
    private String country;
    private Integer feelsLike;
    private String description;
    private String humidity;

}
