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
    Tour getById(long id);
    List<Tour> filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id(TourFilterModel tourModel);
    String validTourDurationAndDayAtSea(int tourDuration, int dayAtSea);
    void updateFieldById(long id, String fieldName, TourEditModel tourModel) throws IOException;
    Tour addModelToEntity(TourAddModel tourAddModel);
}
