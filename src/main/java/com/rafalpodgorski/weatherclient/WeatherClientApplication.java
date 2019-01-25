package com.rafalpodgorski.weatherclient;


import com.rafalpodgorski.weatherclient.model.Temperature;
import com.rafalpodgorski.weatherclient.repository.TemperatureRepository;
import com.rafalpodgorski.weatherclient.service.ConfigReader;
import com.rafalpodgorski.weatherclient.service.TemperatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class WeatherClientApplication {


    private static final Logger log = LoggerFactory.getLogger(WeatherClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WeatherClientApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public CommandLineRunner run(TemperatureService temperatureService, TemperatureRepository temperatureRepository, ConfigReader configReader) throws Exception {
        return args -> {

            final long timeInterval = 30000;
            Runnable runnable = () -> {
                while (true) {

                    for (String city : configReader.readConfig()) {
                        Temperature temperature = getTemperatureForCity(temperatureService, city);
                        System.out.println(temperature);
                        temperatureRepository.save(temperature);
                    }
                    System.out.println("----------------------------------------");

                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
        };

    }

    private Temperature getTemperatureForCity(TemperatureService temperatureService, String city) {
        if (city.equals("auto")) {
            return temperatureService.getTemperatureByGeolocation();
        } else {
            return temperatureService.getTemperatureByCityName(city);
        }
    }

}

