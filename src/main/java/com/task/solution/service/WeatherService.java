package com.task.solution.service;

import com.task.solution.dto.GeoLocation;
import com.task.solution.entity.Location;
import com.task.solution.entity.Weather;
import com.task.solution.exception.yandex.BadResponseFromYandexWeatherException;
import com.task.solution.payload.as_client.yandex.response.YandexWeatherResponse;
import com.task.solution.payload.as_client.yandex.response.GeoObject;
import com.task.solution.payload.as_server.response.WeatherResponse;
import com.task.solution.proxy.WeatherProxy;
import com.task.solution.repository.IWeatherRepository;
import com.task.solution.utils.url.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Transactional
public class WeatherService {
    @Autowired
    private IWeatherRepository weatherRepository;
    @Autowired
    private LocationService locationService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    WeatherProxy weatherProxy;

    @Value("${expiration-time}")
    private Long EXPIRATION_TIME;
    private static final String YANDEX_WEATHER_URL = "https://api.weather.yandex.ru/v2/forecast";
    Date dateNow;

    public WeatherResponse getWeather(String country, String city, String locality, float lat, float lon, String lang) {
        if(country == null || city == null)
            getWeather(lat, lon, lang);
        //search in proxy
        dateNow = new Date(System.currentTimeMillis());
        GeoLocation geoLocation = new GeoLocation(country, city, locality);
        Optional<String> temp = weatherProxy.getTemp(geoLocation, dateNow);
        if(temp.isPresent())
            return new WeatherResponse(geoLocation, temp.get());
        //search in database (if this location exist)
        Optional<Location> geoLocationOpt = locationService.findByGeoLocation(geoLocation);
        if(geoLocationOpt.isPresent()){
            Location location = geoLocationOpt.get();
            Weather dbWeather = weatherRepository.findByLocation(location);
            if(dateNow.getTime() - dbWeather.getDate().getTime() < EXPIRATION_TIME)
                return new WeatherResponse(geoLocation, dbWeather.getValue());
        }
        else
            return getWeather(lat, lon, lang);
        return WeatherResponse.empty();
    }
    public WeatherResponse getWeather(float lat, float lon, String lang) {
        YandexWeatherResponse weatherFromYandex;
        try {
            weatherFromYandex = getWeatherFromYandex(getRequestURLWithParams(lat,lon,lang));
            GeoObject geoObject = weatherFromYandex.getGeoObject();
            GeoLocation geoLocation = new GeoLocation(geoObject);
            Date uptime = new Date(weatherFromYandex.getUptime() * 1000L);
            Short value = weatherFromYandex.getTemp();
            //save location if not exist
            Location location = new Location(geoLocation);
            if (!locationService.existsByGeoLocation(geoLocation))
                locationService.saveLocation(location);
            location = locationService.findByGeoLocation(geoLocation).get();
            //save weather in database and proxy
            saveWeather(new Weather(0L, uptime, uptime, value.toString(), location));
            weatherProxy.saveWeather(geoLocation, uptime, value.toString());
            return new WeatherResponse(geoLocation, value.toString());
        } catch (BadResponseFromYandexWeatherException e) {
            e.printStackTrace();
        }
        return WeatherResponse.empty();
    }

    void saveWeather(Weather weather) {
        Optional<Weather> dbWeatherOpt = Optional.ofNullable(weatherRepository.findByLocation(weather.getLocation()));
        Weather dbWeather;
        if(dbWeatherOpt.isPresent()){
            dbWeather = dbWeatherOpt.get();
            if (dbWeather.getDate().getTime() - weather.getDate().getTime() < EXPIRATION_TIME){
                return;
            }
        }
            weatherRepository.save(weather);
    }


    public YandexWeatherResponse getWeatherFromYandex(String weatherUrlRequest) throws
            BadResponseFromYandexWeatherException {
        //addition headers
        HttpHeaders headers = new HttpHeaders();
        List<String> apiKey = Collections.singletonList("8e7ab086-775a-4cee-ba78-4597a85b4169");
        headers.put("X-Yandex-API-Key", apiKey);;
        //request to yandex weather API
        ResponseEntity<YandexWeatherResponse> responseEntity = restTemplate.exchange(
                weatherUrlRequest, HttpMethod.GET, new HttpEntity<>(headers), YandexWeatherResponse.class);
        if (responseEntity.getStatusCode().is2xxSuccessful())
            return responseEntity.getBody();
        throw new BadResponseFromYandexWeatherException();
    }


    public static String getRequestURLWithParams(float lat, float lon, String lang) {

        String latParam = "lat=" + lat;
        String lonParam = "lon=" + lon;
        String langParam = "lang=" + lang;
        //default
        String limitParam = "limit=1";
        String hoursParam = "hours=false";
        String extraParam = "extra=false";

        return UrlUtils.makeRequestWithParam(YANDEX_WEATHER_URL,
                latParam, lonParam, langParam, limitParam, hoursParam, extraParam);
    }


}
