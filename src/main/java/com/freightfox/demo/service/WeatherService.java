package com.freightfox.demo.service;

import com.freightfox.demo.domain.WeatherData;

import java.time.LocalDate;

public interface WeatherService {
    WeatherData getWeatherData(String pinCode, LocalDate weatherDate);
}
