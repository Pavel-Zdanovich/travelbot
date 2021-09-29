package com.zdanovich.travelbot.controller;

import com.zdanovich.travelbot.domain.City;
import com.zdanovich.travelbot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<City> create(@RequestBody City city) {
        City savedCity = cityService.save(city);
        return ResponseEntity.ok(savedCity);
    }

    @GetMapping
    public ResponseEntity<List<City>> getCities() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<City> getCityById(@RequestParam Long id) {
        Optional<City> optionalCity = cityService.findById(id);
        return optionalCity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(params = "name")
    public ResponseEntity<City> getCityByName(@RequestParam String name) {
        Optional<City> optionalCity = cityService.findByName(name);
        return optionalCity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<City> update(@RequestBody City city) {
        Optional<City> optionalCity = cityService.findByName(city.getName());
        if (optionalCity.isPresent()) {
            City savedCity = optionalCity.get();
            savedCity.setDescription(city.getDescription());
            City updatedCity = cityService.save(savedCity);
            return ResponseEntity.ok(updatedCity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(params = "name")
    public ResponseEntity<City> delete(@RequestParam String name) {
        Optional<City> foundCity = cityService.findByName(name);
        if (foundCity.isPresent()) {
            cityService.delete(foundCity.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
