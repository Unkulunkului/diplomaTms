package by.tms.diploma.service.impl;

import by.tms.diploma.entity.*;

import by.tms.diploma.repository.TourRepository;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;
import by.tms.diploma.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ImageService imageService;

    @Override
    public void save(Tour tour) {
        tourRepository.save(tour);
    }

    @Override
    public boolean existsByName(String name) {
        return tourRepository.existsByName(name);
    }

    @Override
    public boolean existsById(long id) {
        return tourRepository.existsById(id);
    }

    @Override
    public Optional<Tour> findById(long id) {
        return tourRepository.findById(id);
    }

    @Override
    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        tourRepository.deleteById(id);
    }

    @Override
    public void update(Tour tour) {
        if(tour.getImages()!= null){
            long id = tour.getId();
            Tour tourFromBD = tourRepository.getById(id);
            Image tourFromBDImages = tourFromBD.getImages();
            List<String> tourFromBDImageUrls = tourFromBDImages.getUrls();
            String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
            tourFromBDImageUrls.remove(defaultImage);
            List<String> formImageUrls = tour.getImages().getUrls();
            tourFromBDImageUrls.addAll(formImageUrls);
        }
        tourRepository.save(tour);
    }

    @Override
    public Tour getById(long id) {
        return tourRepository.getById(id);
    }

    @Override
    public List<Tour> filterByPriceTourDurationDayAtSeaTypeOfRest(double startPrice, double finishPrice,
                                                                  int startTourDuration, int startDayAtSea,
                                                                  TypeOfRest typeOfRest) {
        return tourRepository.getAllByPriceIsGreaterThanEqualAndPriceLessThanEqualAndTourDurationGreaterThanEqualAndDayAtSeaIsGreaterThanEqualAndTypeOfRestEquals(
                startPrice, finishPrice, startTourDuration, startDayAtSea, typeOfRest);
    }
}
