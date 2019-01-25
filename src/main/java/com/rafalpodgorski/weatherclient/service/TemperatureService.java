package com.rafalpodgorski.weatherclient.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rafalpodgorski.weatherclient.model.Location;
import com.rafalpodgorski.weatherclient.model.Temperature;
import com.rafalpodgorski.weatherclient.repository.TemperatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.rafalpodgorski.weatherclient.model.Constants.API_KEY;

@Service
public class TemperatureService {

    private RestTemplate restTemplate;
    private GeolocationService geolocationService;


    public TemperatureService(RestTemplate restTemplate, GeolocationService geolocationService) {
        this.restTemplate = restTemplate;
        this.geolocationService = geolocationService;

    }

    public Temperature getTemperatureByCityName(String cityName) {

        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&APPID=%s&units=metric", cityName, API_KEY);
        return getTemperature(url);

    }

    public Temperature getTemperatureByGeolocation() {
        Location location = geolocationService.getCoords();
        double lat = location.getLat();
        double lng = location.getLng();
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?lat=%.2f&lon=%.2f&APPID=%s&units=metric", lat, lng, API_KEY);
        return getTemperature(url);


    }

    private Temperature getTemperature(String url) {
        JsonNode info = restTemplate.getForObject(url, JsonNode.class);
        String cityName = info.get("name").asText();
        int temperature = info.get("main").get("temp").asInt();
        return new Temperature(cityName, temperature);
    }


}

