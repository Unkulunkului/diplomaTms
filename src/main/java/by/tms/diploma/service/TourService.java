package by.tms.diploma.service;

import by.tms.diploma.entity.*;


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
    boolean theSameTour(long id, String name);
    Tour getById(long id);
    List<Tour> filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id(double startPrice, double finishPrice, int startTourDuration,
                                                                      int startDayAtSea, TypeOfRest typeOfRest, long startId, long finishId);
    String validTourDurationAndDayAtSea(int tourDuration, int dayAtSea);
    void updateFieldById(long id, String fieldName, TourEditModel tourModel) throws IOException;
}
