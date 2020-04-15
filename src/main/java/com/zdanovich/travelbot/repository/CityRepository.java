package com.zdanovich.travelbot.repository;

import com.zdanovich.travelbot.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    List<City> findAll();
    Optional<City> findByName(String name);

}
