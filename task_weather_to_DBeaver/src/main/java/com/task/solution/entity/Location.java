package com.task.solution.entity;

import com.task.solution.dto.GeoLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    public Location(GeoLocation geoLocation) {
        this.id = 0;
        this.country = geoLocation.getCountry();
        this.city = geoLocation.getCity();
        this.locality = geoLocation.getLocality();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    Integer id;
    @Column(name = "location_country")
    String country;
    @Column(name = "location_city")
    String city;
    @Column(name = "location_locality")
    String locality;

}
