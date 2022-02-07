package com.task.solution.proxy;


import com.task.solution.dto.GeoLocation;
import javafx.util.Pair;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class WeatherProxy {
    @Value(value = "${expiration-time}")
    private Long EXPIRATION_TIME;
    private Map<GeoLocation, Pair<Date,String>> valueAndDateByGeoMap;

    public WeatherProxy() {
        valueAndDateByGeoMap = new HashMap<>();
    }

    public Optional<String> getTemp(GeoLocation geoLocation, Date now) {
        Pair<Date, String> dateTempPair = valueAndDateByGeoMap.get(geoLocation);
        if(dateTempPair == null || now.getTime() - dateTempPair.getKey().getTime() > EXPIRATION_TIME)
            return Optional.empty();
        return Optional.of(dateTempPair.getValue());

    }

    public void saveWeather(GeoLocation geoLocation, Date date, String temperature) {
        valueAndDateByGeoMap.put(geoLocation, new Pair<>(date, temperature));
    }
}
