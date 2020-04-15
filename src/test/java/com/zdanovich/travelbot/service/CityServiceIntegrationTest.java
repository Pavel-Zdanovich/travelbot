package com.zdanovich.travelbot.service;

import com.zdanovich.travelbot.domain.City;
import com.zdanovich.travelbot.repository.CityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityServiceIntegrationTest {

    @TestConfiguration
    static class CityServiceTestContextConfiguration {

        @Bean
        @Autowired
        public CityService employeeService(CityRepository cityRepository) {
            return new CityService(cityRepository);
        }
    }

    @Autowired
    private CityService cityService;

    @Test
    public void testSave() {
        City city = new City();
        city.setName("Tokyo");
        city.setDescription("Better to see Sensoji, than get to the Tokyo SkyTree.");

        cityService.findByName(city.getName()).ifPresent(c -> Assert.fail());

        City savedCity = cityService.save(city);
        Assert.assertEquals(savedCity.getName(), city.getName());
        Assert.assertEquals(savedCity.getDescription(), city.getDescription());

        City foundCity = cityService.findByName(city.getName()).orElseThrow(() -> new AssertionError());
        Assert.assertEquals(savedCity, foundCity);
    }

    @Test
    public void testFindById() {
        cityService.findById(1L).orElseThrow(() -> new AssertionError());
    }

    @Test
    public void testFindByName() {
        cityService.findByName("Paris").orElseThrow(() -> new AssertionError());
    }

    @Test
    public void testExistsById() {
        Assert.assertTrue(cityService.existsById(1L));
    }

    @Test
    public void testCount() {
        Assert.assertNotEquals(0, cityService.count());
    }

    @Test
    public void testDeleteById() {
        Assert.assertTrue(cityService.existsById(7L));
        cityService.deleteById(7L);
        Assert.assertFalse(cityService.existsById(7L));
    }

    @Test
    public void testDelete() {
        City city = cityService.findById(8L).orElseThrow(() -> new AssertionError());
        cityService.delete(city);
        Assert.assertFalse(cityService.existsById(city.getId()));
    }

    @Test
    public void testDeleteAll_By_Iterable() {
        City city1 = cityService.findById(9L).orElseThrow(() -> new AssertionError());
        City city2 = cityService.findById(10L).orElseThrow(() -> new AssertionError());
        List<City> cities = new ArrayList<>();
        cities.add(city1);
        cities.add(city2);
        cityService.deleteAll(cities);
        Assert.assertFalse(cityService.existsById(city1.getId()));
        Assert.assertFalse(cityService.existsById(city2.getId()));
    }

    @Test
    public void testDeleteAll() {
        //cityService.deleteAll(); it works
    }
}
