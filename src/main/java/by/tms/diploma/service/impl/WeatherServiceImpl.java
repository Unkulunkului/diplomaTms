package by.tms.diploma.service.impl;

import by.tms.diploma.entity.Weather;
import by.tms.diploma.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Weather getWeather(String city){
        String url = "http://api.weatherbit.io/v2.0/forecast/daily?key=6152203e44da4d0d8f0ac2ff00f5d159&lang=eng&" +
                "city="+city+"&days=3";
        ResponseEntity<Weather> forEntity = restTemplate.getForEntity(url, Weather.class);
        return forEntity.getBody();
    }
}
