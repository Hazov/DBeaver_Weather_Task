package com.task.solution.controller;

import com.task.solution.payload.as_server.response.WeatherResponse;
import com.task.solution.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/weather")
public class WeatherController {
    @Autowired
    WeatherService weatherService;

    @GetMapping()
    ResponseEntity<WeatherResponse> getWeather(@RequestParam float lat,
                                               @RequestParam float lon,
                                               @RequestParam String lang,
                                               @RequestParam(required = false) String country,
                                               @RequestParam(required = false) String city,
                                               @RequestParam (required = false) String locality)
    {

        WeatherResponse weatherResponse;
        if (country != null && city != null)
            weatherResponse = weatherService.getWeather(country, city, locality, lat, lon, lang);
        else
            weatherResponse = weatherService.getWeather(lat, lon, lang);
        return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
    }


}
