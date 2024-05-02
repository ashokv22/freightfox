package com.freightfox.demo.rest;

import com.freightfox.demo.domain.WeatherData;
import com.freightfox.demo.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class WeatherInfoResourceTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherInfoResource weatherInfoResource;

    @Mock
    private WeatherData expectedWeatherData;

    @Test
    public void testGetWeatherData_returnsWeatherData() {
        String pinCode = "12345";
        LocalDate weatherDate = LocalDate.now();

        // Mock weatherService behavior
        Mockito.when(weatherService.getWeatherData(pinCode, weatherDate)).thenReturn(expectedWeatherData);

        // Call the API endpoint
        WeatherData actualWeatherData = weatherInfoResource.getWeatherData(pinCode, weatherDate);
        // Assertions
        assertEquals(expectedWeatherData, actualWeatherData);
        Mockito.verify(weatherService, times(1)).getWeatherData(pinCode, weatherDate);
    }
}
