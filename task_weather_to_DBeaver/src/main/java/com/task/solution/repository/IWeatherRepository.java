package com.task.solution.repository;

import com.task.solution.entity.Location;
import com.task.solution.entity.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWeatherRepository extends CrudRepository<Weather, Long> {
    Weather findByLocation(Location location);

}
