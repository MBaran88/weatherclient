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
        BufferedReader br = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            br = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                cities.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cities;

    }
}
