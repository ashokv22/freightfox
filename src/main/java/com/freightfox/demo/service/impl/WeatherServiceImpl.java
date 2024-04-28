package com.freightfox.demo.service.impl;

import com.freightfox.demo.domain.Pincode;
import com.freightfox.demo.domain.WeatherData;
import com.freightfox.demo.feign.OpenWeatherFeign;
import com.freightfox.demo.repository.PincodeRepository;
import com.freightfox.demo.repository.WeatherDataRepository;
import com.freightfox.demo.service.WeatherService;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Value("${apis.openWeather.api-key}")
    private String openWeatherApiKey;

    private final PincodeRepository pincodeRepository;
    private final WeatherDataRepository weatherDataRepository;
    private final OpenWeatherFeign openWeatherFeign;

    @Autowired
    public WeatherServiceImpl(PincodeRepository pincodeRepository, WeatherDataRepository weatherDataRepository, OpenWeatherFeign openWeatherFeign) {
        this.pincodeRepository = pincodeRepository;
        this.weatherDataRepository = weatherDataRepository;
        this.openWeatherFeign = openWeatherFeign;
    }

    @Override
    public WeatherData getWeatherData(String pinCode, LocalDate weatherDate) {
        log.info("Getting weather data for {} on {}", pinCode, weatherDate);
        long unixDate = weatherDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond();
        Optional<Pincode> pincodeOptional = pincodeRepository.findByPincode(pinCode);
        if (pincodeOptional.isPresent()) {
            log.info("Found pincode in table for: {}", pinCode);
            return weatherDataRepository.findByPincode(pinCode);
        }
        String latLong = getLatLong(pinCode);
        String[] latLongSep = latLong.split(",");
        log.info("Lat Long: {}", latLongSep[0] + " " + latLongSep[1]);
        JsonObject response = openWeatherFeign.getWeatherData(latLongSep[0], latLongSep[1], openWeatherApiKey, String.valueOf(unixDate));
        return saveWeatherData(pinCode, weatherDate, response, latLongSep);
    }

    private WeatherData saveWeatherData(String pinCode, LocalDate weatherDate, JsonObject response, String[] latLongSep) {
        log.info("Saving weather data for {} on {}", pinCode, weatherDate);
        WeatherData weatherData = new WeatherData();
        weatherData.setPincode(pinCode);
        weatherData.setTemperature(response.get("main").getAsJsonObject().get("temp").getAsDouble());
        weatherData.setDescription(response.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
        weatherData.setHumidity(response.get("main").getAsJsonObject().get("humidity").getAsDouble());
        weatherData.setForDate(weatherDate);

        log.info("Saving weather data {}", weatherData);
        weatherDataRepository.save(weatherData);

        Pincode pincodeData = new Pincode();
        pincodeData.setPincode(pinCode);
        pincodeData.setLatitude(Double.valueOf(latLongSep[0]));
        pincodeData.setLongitude(Double.valueOf(latLongSep[1]));

        log.info("Saving pincode {}", pincodeData);
        pincodeRepository.save(pincodeData);
        return weatherData;
    }

    private String getLatLong(String pinCode) {
        log.info("Getting lat long for {}", pinCode);
        String pinCodeWithCountryCode = pinCode + ",IN";
        JsonObject geoDecoding = openWeatherFeign.getGeoDecoding(pinCodeWithCountryCode, openWeatherApiKey);
        return geoDecoding.get("lat").getAsString() + "," + geoDecoding.get("lon").getAsString();
    }

}
