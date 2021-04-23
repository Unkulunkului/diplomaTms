package by.tms.diploma.service;

import by.tms.diploma.entity.Tour;

import by.tms.diploma.entity.TourAddModel;
import by.tms.diploma.entity.TypeOfRest;


import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface TourService {

    Tour save(Tour tour);
    boolean existsByName(String name);
    boolean existsById(long id);
    Optional<Tour> findById(long id);
    List<Tour> getAll();
    void deleteById(long id);
    void update(Tour tour);
    Tour getById(long id);
    List<Tour> filterByPriceTourDurationDayAtSeaTypeOfRest(double startPrice, double finishPrice, int startTourDuration,
                                                           int startDayAtSea, TypeOfRest typeOfRest);
    String validTourDurationAndDayAtSea(int tourDuration, int dayAtSea);
}
