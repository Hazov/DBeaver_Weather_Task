package com.task.solution.payload.as_server.request;

import lombok.Data;

@Data
public class WeatherRequest {
    float lat;
    float lon;
}
