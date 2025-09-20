package org.weatherApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CityInfoDto {

    private String name;
    private String latitude;
    private String longitude;
    private String country;
    private String state;
}
