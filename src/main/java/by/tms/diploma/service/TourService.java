package by.tms.diploma.service;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import org.springframework.stereotype.Service;


public interface TourService {

    void add(Tour tour);
    boolean existsByName(String name);
}
