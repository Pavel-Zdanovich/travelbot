package com.zdanovich.travelbot.service;

import com.zdanovich.travelbot.domain.City;
import com.zdanovich.travelbot.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityService {

    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public Optional<City> findById(Long cityId) {
        return cityRepository.findById(cityId);
    }

    public Optional<City> findByName(String name) {
        return cityRepository.findByName(name);
    }

    public boolean existsById(Long cityId) {
        return cityRepository.existsById(cityId);
    }

    public long count() {
        return cityRepository.count();
    }

    public void deleteById(Long cityId) {
        cityRepository.deleteById(cityId);
    }

    public void delete(City city) {
        cityRepository.delete(city);
    }

    public void deleteAll(Iterable<? extends City> iterable) {
        cityRepository.deleteAll(iterable);
    }

    public void deleteAll() {
        cityRepository.deleteAll();
    }
}
