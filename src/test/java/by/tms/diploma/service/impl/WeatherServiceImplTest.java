package by.tms.diploma.service.impl;

import by.tms.diploma.entity.DayWeather;
import by.tms.diploma.entity.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class WeatherServiceImplTest {


    @Autowired
    private WeatherServiceImpl weatherService;

    @MockBean
    private RestTemplate restTemplate;

    private final String CITY_NAME = "Minsk";
    private final String URL = "weather.com/api/getWeather?day=1&city="+CITY_NAME;

    private Weather weather;

    @BeforeEach
    void setUp() {
        weather = new Weather();
        weather.setCity_name(CITY_NAME);
        DayWeather dayWeather = new DayWeather();
        dayWeather.setTemp(12);
        dayWeather.setValid_date(LocalDate.now());
        weather.setData(Arrays.asList(dayWeather));
    }

    @Test
    void getWeather() {
        ResponseEntity responseEntity = new ResponseEntity(weather, HttpStatus.OK);
        Mockito.when(restTemplate.getForEntity(URL, Weather.class)).thenReturn(responseEntity);
        Weather actual = weatherService.getWeather(CITY_NAME);
        assertEquals(weather, actual);
    }
}