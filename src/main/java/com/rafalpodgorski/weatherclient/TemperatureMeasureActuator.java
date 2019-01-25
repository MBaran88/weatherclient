package com.rafalpodgorski.weatherclient;

import com.rafalpodgorski.weatherclient.model.Temperature;
import com.rafalpodgorski.weatherclient.repository.TemperatureRepository;

import com.rafalpodgorski.weatherclient.service.ConfigReader;
import com.rafalpodgorski.weatherclient.service.TemperatureService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TemperatureMeasureActuator {

    private ConfigReader configReader;
    private TemperatureService temperatureService;
    private TemperatureRepository temperatureRepository;

    public TemperatureMeasureActuator(ConfigReader configReader, TemperatureService temperatureService, TemperatureRepository temperatureRepository) {
        this.configReader = configReader;
        this.temperatureService = temperatureService;
        this.temperatureRepository = temperatureRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void beginTemperatureMeasure() {
        for (String city : configReader.readConfig()) {
            Temperature temperature = getTemperatureForCity(temperatureService, city);
            System.out.println(temperature);
            temperatureRepository.save(temperature);
        }
        System.out.println("----------------------------------------------");
    }

    private Temperature getTemperatureForCity(TemperatureService temperatureService, String city) {
        if (city.equals("auto")) {
            return temperatureService.getTemperatureByGeolocation();
        } else {
            return temperatureService.getTemperatureByCityName(city);
        }
    }
}
