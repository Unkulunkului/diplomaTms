package by.tms.diploma.service;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import by.tms.diploma.entity.TourUpdateModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TourService {

    void save(Tour tour);
    boolean existsByName(String name);
    boolean existsById(long id);
    Optional<Tour> getById(long id);
    List<Tour> getAll();
    void deleteById(long id);
    void updateTour(TourUpdateModel tour);
}
