package org.weatherApp.util;

import java.util.HashMap;
import java.util.Map;

public class WeatherCodeMap {

    private final static Map<String, String> WEATHER_CODES =createWeatherMap();

    private static Map<String, String> createWeatherMap() {
        Map<String, String> map = new HashMap<>();

        map.put("clear sky", "01n");
        map.put("overcast clouds", "04n");
        map.put("broken clouds", "04n");
        map.put("scattered clouds", "03n");
        map.put("few clouds", "02n");
        map.put("light rain", "10n");
        map.put("moderate rain", "10n");
        map.put("heavy intensity rain", "10n");
        map.put("very heavy rain", "10n");
        map.put("extreme rain", "10n");
        map.put("freezing rain", "13n");
        map.put("light intensity shower rain", "09n");
        map.put("shower rain", "09n");
        map.put("heavy intensity shower rain", "09n");
        map.put("ragged shower rain", "09n");
        map.put("thunderstorm with light rain", "11n");
        map.put("thunderstorm with rain", "11n");
        map.put("thunderstorm with heavy rain", "11n");
        map.put("light thunderstorm", "11n");
        map.put("thunderstorm", "11n");
        map.put("heavy thunderstorm", "11n");
        map.put("ragged thunderstorm", "11n");
        map.put("thunderstorm with light drizzle", "11n");
        map.put("thunderstorm with drizzle", "11n");
        map.put("thunderstorm with heavy drizzle", "11n");
        map.put("light snow", "13n");
        map.put("snow", "13n");
        map.put("heavy snow", "13n");
        map.put("sleet", "13n");
        map.put("light shower sleet", "13n");
        map.put("shower sleet", "13n");
        map.put("light rain and snow", "13n");
        map.put("rain and snow", "13n");
        map.put("light shower snow", "13n");
        map.put("shower snow", "13n");
        map.put("heavy shower snow", "13n");
        map.put("mist", "50n");
        map.put("smoke", "50n");
        map.put("haze", "50n");
        map.put("sand/dust whirls", "50n");
        map.put("fog", "50n");
        map.put("sand", "50n");
        map.put("dust", "50n");
        map.put("volcanic ash", "50n");
        map.put("squalls", "50n");
        map.put("tornado", "50n");
        map.put("light intensity drizzle", "09n");
        map.put("drizzle", "09n");
        map.put("heavy intensity drizzle", "09n");
        map.put("light intensity drizzle rain", "09n");
        map.put("drizzle rain", "09n");
        map.put("heavy intensity drizzle rain", "09n");
        map.put("shower rain and drizzle", "09n");
        map.put("heavy shower rain and drizzle", "09n");
        map.put("shower drizzle", "09n");

        return Map.copyOf(map);
    }

    public static Map<String, String> getWeatherCodes(){
        return WEATHER_CODES;
    }

}
