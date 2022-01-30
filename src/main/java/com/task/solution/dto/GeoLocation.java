package com.task.solution.dto;

import com.task.solution.payload.as_client.yandex.response.GeoObject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class GeoLocation {
    String country;
    String city;
    String locality;

    public GeoLocation(GeoObject geoObject){
        this.country = geoObject.getCountry();
        this.city = geoObject.getProvince();
        this.locality = geoObject.getLocality();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocation that = (GeoLocation) o;
        return country.equals(that.country) && city.equals(that.city) && Objects.equals(locality, that.locality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, locality);
    }
}
