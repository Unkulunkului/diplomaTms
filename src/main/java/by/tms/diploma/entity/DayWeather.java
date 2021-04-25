package by.tms.diploma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class DayWeather {
    private LocalDate valid_date;
    private double temp;
    private WeatherIcon weather;
}
