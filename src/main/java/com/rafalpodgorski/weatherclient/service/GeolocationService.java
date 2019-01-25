package com.rafalpodgorski.weatherclient.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.rafalpodgorski.weatherclient.model.Location;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static com.rafalpodgorski.weatherclient.model.Constants.MLS_URL;

@Service
public class GeolocationService {

    private RestTemplate restTemplate;

    public GeolocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Location getCoords(){
        JsonNode response = restTemplate.getForObject(MLS_URL, JsonNode.class);

        double lat = response.get("location").get("lat").asDouble();
        double lng = response.get("location").get("lng").asDouble();


        return new Location(lat, lng);

    }
}
