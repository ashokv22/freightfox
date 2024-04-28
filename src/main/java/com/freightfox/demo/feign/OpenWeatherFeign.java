package com.freightfox.demo.feign;

import com.google.gson.JsonObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openWeatherFeign", url = "${apis.openWeather.baseUrl}", configuration = {DefaultConfiguration.class})
public interface OpenWeatherFeign {

    @GetMapping("/geo/1.0/zip")
    JsonObject getGeoDecoding(
            @RequestParam String zip,
            @RequestParam String appid
    );

    @GetMapping("/data/2.5/weather")
    JsonObject getWeatherData(
            @RequestParam String lat,
            @RequestParam String lon,
            @RequestParam String appid,
            @RequestParam String units
    );

}
