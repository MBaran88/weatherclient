package com.rafalpodgorski.weatherclient.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
public class ConfigReader {

    public Set<String> readConfig() {
        Set<String> cities = new LinkedHashSet<>();
        String path = "./config.txt";


        try (FileInputStream fileInputStream = new FileInputStream(new File(path))) {
            Scanner s = new Scanner(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            while (s.hasNextLine()) {
                cities.add(s.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
