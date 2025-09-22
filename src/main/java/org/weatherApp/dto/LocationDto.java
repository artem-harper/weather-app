package org.weatherApp.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private Integer id;
    private String name;
    private UserDto userId;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
