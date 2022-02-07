package com.task.solution.payload.as_server.response;

import com.task.solution.dto.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@AllArgsConstructor
public class WeatherResponse {
    GeoLocation location;
    String temp;

    private WeatherResponse() {
        location = null;
        temp = null;
    }

    public static WeatherResponse empty(){
        return new WeatherResponse();
    }
    public boolean isEmpty(){
        return this.location == null || this.temp == null;
    }
}
