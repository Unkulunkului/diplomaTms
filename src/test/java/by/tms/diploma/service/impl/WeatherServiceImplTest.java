package by.tms.diploma.service.impl;

import by.tms.diploma.entity.DayWeather;
import by.tms.diploma.entity.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WeatherServiceImplTest {

    @Autowired
    private WeatherServiceImpl weatherService;

    @MockBean
    private RestTemplate restTemplate;

    private final String CITY_NAME = "Minsk";
    private final String URL = "http://api.weatherbit.io/v2.0/forecast/daily?key=6152203e44da4d0d8f0ac2ff00f5d159&lang=eng&" +
            "city="+CITY_NAME+"&days=3";
    private ResponseEntity responseEntity;
    private Weather expected;

    @BeforeEach
    void setUp() {
        expected = new Weather();
        DayWeather firstDayWeather = new DayWeather();
        firstDayWeather.setTemp(11);
        DayWeather secondDayWeather = new DayWeather();
        secondDayWeather.setTemp(16);
        expected.setData(Arrays.asList(firstDayWeather, secondDayWeather));
        HttpStatus statusCode = HttpStatus.OK;
        responseEntity = new ResponseEntity<>(expected, statusCode);
    }

    @Test
    void getWeather() {
        Mockito.when(restTemplate.getForEntity(URL, Weather.class)).thenReturn(responseEntity);
        Weather actual = weatherService.getWeather(CITY_NAME);
        assertEquals(expected, actual);
    }
}