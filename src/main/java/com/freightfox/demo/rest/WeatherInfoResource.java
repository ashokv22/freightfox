package com.freightfox.demo.rest;

import com.freightfox.demo.domain.WeatherData;
import com.freightfox.demo.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/weather")
@Slf4j
@AllArgsConstructor
public class WeatherInfoResource {

    private WeatherService weatherService;

    @GetMapping("/get-weather-data")
    public WeatherData getWeatherData(@RequestParam String pinCode, @RequestParam LocalDate weatherDate) {
        log.info("REST request to get weather data for {} on {}", pinCode, weatherDate);
        return weatherService.getWeatherData(pinCode, weatherDate);
    }
}
