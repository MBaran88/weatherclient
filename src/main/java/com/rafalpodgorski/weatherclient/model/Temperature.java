package com.rafalpodgorski.weatherclient.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Temperature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private int temp;

    public Temperature(String city, int temp) {
        this.city = city;
        this.temp = temp;
    }


    @Override
    public String toString() {
        return String.format("Temperature in %s is %d Celsius degree.", city, temp);
    }

    public String getCity() {
        return city;
    }
}
