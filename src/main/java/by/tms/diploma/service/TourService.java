package by.tms.diploma.service;

import by.tms.diploma.entity.Tour;

import by.tms.diploma.entity.TourAddModel;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface TourService {

    void save(Tour tour);
    boolean existsByName(String name);
    boolean existsById(long id);
    Optional<Tour> findById(long id);
    List<Tour> getAll();
    void deleteById(long id);
    void update(long id, TourAddModel tour) throws IOException;
    Tour getById(long id);
}
