package com.rafalpodgorski.weatherclient.repository;

import com.rafalpodgorski.weatherclient.model.Temperature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TemperatureRepository extends CrudRepository<Temperature, Long> {
        

}
