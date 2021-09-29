package com.zdanovich.travelbot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "CITY")
public class City implements Serializable {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CITY_SEQUENCE")
    @SequenceGenerator(name = "CITY_SEQUENCE", sequenceName = "CITY_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    @NotEmpty(message = "City name must not be empty")
    @Size(min = 1, max = 255, message = "City name must be between {min} and {max} characters")
    private String name;

    @Column(name = "DESCRIPTION")
    @NotEmpty(message = "City description must not be empty")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return id.equals(city.id) &&
                name.equals(city.name) &&
                description.equals(city.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
