package com.task.solution.payload.as_client.yandex.request;

import lombok.Getter;

@Getter
public class YandexWeatherRequest {
    private String lat;
    private String lon;
    private String lang;
    private String limit;
    private  String hours;
    private String extra;

}
