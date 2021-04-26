package by.tms.diploma.service.impl;

import by.tms.diploma.entity.*;

import by.tms.diploma.repository.TourRepository;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;
import by.tms.diploma.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ImageService imageService;

    @Override
    public Tour save(Tour tour) {
        return tourRepository.save(tour);
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
        long id = tour.getId();
        Tour tourFromBD = tourRepository.getById(id);
        log.info(tour.toString());
        if(tour.getImages()!= null){
            Image tourFromBDImages = tourFromBD.getImages();
            List<String> tourFromBDImageUrls = tourFromBDImages.getUrls();
            String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
            tourFromBDImageUrls.remove(defaultImage);
            List<String> formImageUrls = tour.getImages().getUrls();
            tourFromBDImageUrls.addAll(formImageUrls);
        }else{
            tour.setImages(tourFromBD.getImages());
        }
        tourRepository.save(tour);
    }

    @Override
    public Tour getById(long id) {
        return tourRepository.getById(id);
    }

    @Override
    public List<Tour> filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id(double startPrice, double finishPrice,
                                                                             int startTourDuration, int startDayAtSea,
                                                                             TypeOfRest typeOfRest, long startId, long finishId) {
        return tourRepository.getAllByPriceIsGreaterThanEqualAndPriceLessThanEqualAndTourDurationGreaterThanEqualAndDayAtSeaIsGreaterThanEqualAndTypeOfRestEqualsAndHotel_IdGreaterThanEqualAndHotel_IdLessThanEqual(
                startPrice, finishPrice, startTourDuration, startDayAtSea, typeOfRest, startId, finishId);
    }

    @Override
    public String validTourDurationAndDayAtSea(int tourDuration, int dayAtSea){
        if(tourDuration < dayAtSea){
            return "Tour duration can't be less than day at sea!";
        }else if(dayAtSea > 39){
            return "Incorrect 'day at sea'. Please enter a number less than 39 and great than 2";
        }else if(tourDuration > 40 || tourDuration < 4){
            return "Incorrect 'tour duration'. Please enter a number less than 40 and great than 4";
        }else{
            return "Ok";
        }
    }

    @Override
    public void updateFieldById(long id, String fieldName, TourEditModel tourModel) throws IOException {
        Tour tour = tourRepository.getById(id);
        switch (fieldName) {
            case "name":
                String formName = tourModel.getName();
                if(!formName.isEmpty()){
                    tour.setName(formName);
                }
                break;

            case "hotelName":
                String formHotelName = tourModel.getHotelName();
                if(!formHotelName.isEmpty() && hotelService.existsByName(formHotelName)){
                    Hotel byName = hotelService.findByName(formHotelName);
                    tour.setHotel(byName);
                }
                break;
            case "visitedCountries":
                String formCountries = tourModel.getVisitedCountries();
                if(!formCountries.isEmpty()){
                    tour.setVisitedCountries(formCountries);
                }
                break;
            case "tourDuration":
                String formTourDuration = tourModel.getTourDuration();
                if(!formTourDuration.isEmpty()){
                    tour.setTourDuration(Integer.parseInt(formTourDuration));
                }
                String formDayAtSea = tourModel.getDayAtSea();
                if(!formDayAtSea.isEmpty()){
                    tour.setDayAtSea(Integer.parseInt(formDayAtSea));
                }
                break;
            case "description":
                tour.setDescription(tourModel.getDescription());
                break;

            case "typeOfRest":
                tour.setTypeOfRest(tourModel.getTypeOfRest());
                break;

            case "price":
                String formPrice = tourModel.getPrice();
                if(!formPrice.isEmpty()){
                    tour.setPrice(Double.parseDouble(formPrice));
                }
                break;

            case "images":
                List<MultipartFile> formImages = tourModel.getImages();
                if (formImages != null) {
                    Image uploadedHotelImage = imageService.upload(formImages, "hotel", id);
                    String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
                    Image hotelImages = tour.getImages();
                    List<String> imagesUrls = hotelImages.getUrls();
                    imagesUrls.remove(defaultImage);
                    imagesUrls.addAll(uploadedHotelImage.getUrls());
                }
                break;
        }
        tourRepository.save(tour);
    }

    @Override
    public boolean theSameTour(long id, String name){
        Optional<Tour> byId = tourRepository.findById(id);
        Optional<Tour> byName = tourRepository.getByName(name);
        if (byId.isPresent() && byName.isPresent()){
            return byId.equals(byName);
        }
        return false;
    }
}
