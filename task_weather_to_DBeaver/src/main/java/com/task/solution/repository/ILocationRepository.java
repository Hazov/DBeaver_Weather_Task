package com.task.solution.repository;

import com.task.solution.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public interface ILocationRepository extends CrudRepository<Location, Integer> {
    boolean existsByCountryAndCityAndLocality(String country, String city, String locality);
    Optional<Location> findByCountryAndCityAndLocality(String country, String city, String locality);

}
