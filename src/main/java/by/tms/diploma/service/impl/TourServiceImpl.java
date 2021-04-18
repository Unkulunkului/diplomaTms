package by.tms.diploma.service.impl;

import by.tms.diploma.entity.*;

import by.tms.diploma.repository.TourRepository;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;
import by.tms.diploma.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
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
    public void update(long id, TourAddModel tourModel) throws IOException {
        Tour tour = tourRepository.getById(id);
        tour.setName(tourModel.getName());
        Hotel hotelByName = hotelService.findByName(tourModel.getHotelName());
        tour.setHotel(hotelByName);
        tour.setDescription(tourModel.getDescription());
        tour.setDayAtSea(Integer.parseInt(tourModel.getDayAtSea()));
        tour.setTourDuration(Integer.parseInt(tourModel.getTourDuration()));
        tour.setVisitedCountries(tourModel.getVisitedCountries());
        tour.setTypeOfRest(TypeOfRest.getName(tourModel.getTypeOfRest()));
        tour.setPrice(Double.parseDouble(tourModel.getPrice()));

        if(tourModel.getImages()!= null){
            Image hotelImages = tour.getImages();
            List<String> urls = hotelImages.getUrls();
            String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
            urls.remove(defaultImage);
            Image image = imageService.upload(tourModel.getImages(), "hotel", tour.getId());
            urls.addAll(image.getUrls());
            tour.setImages(image);
        }
        tourRepository.save(tour);
    }

    @Override
    public Tour getById(long id) {
        return tourRepository.getById(id);
    }
}
