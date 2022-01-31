package com.task.solution.service;

import com.task.solution.dto.GeoLocation;
import com.task.solution.entity.Location;
import com.task.solution.repository.ILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LocationService {
    @Autowired
    private ILocationRepository locationRepository;

    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    public boolean existsByGeoLocation(GeoLocation geoLocation) {
        String country = geoLocation.getCountry();
        String city = geoLocation.getCity();
        String locality = geoLocation.getLocality();
        return locationRepository.existsByCountryAndCityAndLocality(country, city, locality);
    }

    public Optional<Location>findByGeoLocation(GeoLocation geoLocation) {
        String country = geoLocation.getCountry();
        String city = geoLocation.getCity();
        String locality = geoLocation.getLocality();
        return locationRepository.findByCountryAndCityAndLocality(country, city, locality);

    }
}
