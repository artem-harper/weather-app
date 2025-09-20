package org.weatherApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityInfoDto {

    private String name;
    private String lat;
    private String lon;
    private String country;
    private String state;
}
