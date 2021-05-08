package by.tms.diploma.service.impl;

import by.tms.diploma.entity.*;

import by.tms.diploma.repository.TourRepository;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;
import by.tms.diploma.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
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
        log.info("Save tour: "+tour);
        return tourRepository.save(tour);
    }

    @Override
    public boolean existsByName(String name) {
        log.info("Exist tour by name(name = "+name+")");
        return tourRepository.existsByName(name);
    }

    @Override
    public boolean existsById(long id) {
        log.info("Exist tour by id(id = "+id+")");
        return tourRepository.existsById(id);
    }

    @Override
    public Optional<Tour> findById(long id) {
        log.info("Find tour by id(id = "+id+")");
        return tourRepository.findById(id);
    }

    @Override
    public List<Tour> getAll() {
        log.info("Find all tours");
        return tourRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        log.info("Delete tour by id(id = "+id+")");
        tourRepository.deleteById(id);
    }

    @Override
    public Tour getById(long id) {
        log.info("Find hotel by id(id = "+id+")");
        return tourRepository.getById(id);
    }

    @Override
    public Tour addModelToEntity(TourAddModel tourAddModel){
        String hotelName = tourAddModel.getHotelName();
        Hotel hotel = hotelService.findByName(hotelName);
        int dayAtSea = Integer.parseInt(tourAddModel.getDayAtSea());
        int tourDuration = Integer.parseInt(tourAddModel.getTourDuration());
        Tour tour = new Tour();
        tour.setHotel(hotel);
        tour.setName(tourAddModel.getName());
        tour.setDescription(tourAddModel.getDescription());
        tour.setDayAtSea(dayAtSea);
        tour.setTourDuration(tourDuration);
        tour.setPrice(Double.parseDouble(tourAddModel.getPrice()));
        tour.setVisitedCountries(tourAddModel.getVisitedCountries());
        tour.setTypeOfRest(TypeOfRest.getEnumName(tourAddModel.getTypeOfRest()));
        Image image = new Image();
        image.setUrl("https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg");
        List<Image> images = new ArrayList<>();
        images.add(image);
        tour.setImages(images);
        return tour;
    }

    @Override
    public List<Tour> filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id(TourFilterModel tourModel) {
        log.info("Find tour by tour edit model: "+tourModel.toString());
        double startPrice = 0;
        double finishPrice = 999999.99d;
        int startTourDuration = 0;
        int startDayAtSea = 0;
        long startId = 0;
        long finishId = 0;
        String hotelName = tourModel.getHotelName();
        if(hotelName != null){
            log.info("Hotel name isn't empty");
            if (hotelService.existsByName(hotelName)) {
                log.info("Hotel with name "+tourModel.getHotelName()+" is exist");
                Hotel byName = hotelService.findByName(hotelName);
                log.info("Hotel: "+byName.toString());
                startId = byName.getId();
                finishId = byName.getId();
            }
        }else {
            log.info("Hotel name is empty");
            finishId = Long.MAX_VALUE;
        }
        if(tourModel.getStartPrice() != null){
            log.info("Start price isn't empty");
            startPrice = Double.parseDouble(tourModel.getStartPrice());
        }
        if(tourModel.getFinishPrice() != null){
            log.info("Finish price isn't empty");
            finishPrice = Double.parseDouble(tourModel.getFinishPrice());
        }
        if(tourModel.getTourDuration() != null){
            log.info("Tour duration isn't empty");
            startTourDuration = Integer.parseInt(tourModel.getTourDuration());
        }
        if(tourModel.getDayAtSea() != null){
            log.info("Day at sea isn't empty");
            startDayAtSea = Integer.parseInt(tourModel.getDayAtSea());
        }
        TypeOfRest type = TypeOfRest.getEnumName(tourModel.getTypeOfRest());
        List<Tour> tours = tourRepository.getAllByPriceIsGreaterThanEqualAndPriceLessThanEqualAndTourDurationGreaterThanEqualAndDayAtSeaIsGreaterThanEqualAndTypeOfRestEqualsAndHotel_IdGreaterThanEqualAndHotel_IdLessThanEqual(
                startPrice, finishPrice, startTourDuration, startDayAtSea, type, startId, finishId);

        return tours;
    }

    @Override
    public String validTourDurationAndDayAtSea(int tourDuration, int dayAtSea){
        log.info("Validation tour duration by tour duration("+tourDuration+") and day at sea("+dayAtSea+")");
        if(tourDuration < dayAtSea){
            log.info("Tour duration can't be less than day at sea!");
            return "Tour duration less than day at sea";
        }else if(dayAtSea > 39){
            log.info("Incorrect 'day at sea'");
            return "Incorrect 'day at sea'. Please enter a number less than 39 and great than 2";
        }else if(tourDuration > 40 || tourDuration < 4){
            log.info("Incorrect 'tour duration'");
            return "Incorrect 'tour duration'. Please enter a number less than 40 and great than 4";
        }else{
            log.info("Result is ok");
            return "Ok";
        }
    }

    @Override
    public void updateFieldById(long id, String fieldName, TourEditModel tourModel) throws IOException {
        log.info("Update tour field("+fieldName+") by id(id = "+id+")");
        log.info("Tour update model: "+tourModel.toString());
        Tour tour = tourRepository.getById(id);
        switch (fieldName) {
            case "name":
                String formName = tourModel.getName();
                if(!formName.isEmpty()){
                    tour.setName(formName);
                    log.info("Field 'name' is set");
                }
                break;

            case "hotelName":
                String formHotelName = tourModel.getHotelName();
                if(!formHotelName.isEmpty() && hotelService.existsByName(formHotelName)){
                    Hotel byName = hotelService.findByName(formHotelName);
                    tour.setHotel(byName);
                    log.info("Field 'hotel' is set");
                }
                break;
            case "visitedCountries":
                String formCountries = tourModel.getVisitedCountries();
                if(!formCountries.isEmpty()){
                    tour.setVisitedCountries(formCountries);
                    log.info("Field 'visited countries' is set");
                }
                break;
            case "tourDuration":
                String formTourDuration = tourModel.getTourDuration();
                if(!formTourDuration.isEmpty()){
                    tour.setTourDuration(Integer.parseInt(formTourDuration));
                    log.info("Field 'tour duration' is set");
                }
                String formDayAtSea = tourModel.getDayAtSea();
                if(!formDayAtSea.isEmpty()){
                    tour.setDayAtSea(Integer.parseInt(formDayAtSea));
                    log.info("Field 'day at sea' is set");
                }
                break;
            case "description":
                tour.setDescription(tourModel.getDescription());
                log.info("Field 'description' is set");
                break;

            case "typeOfRest":
                tour.setTypeOfRest(tourModel.getTypeOfRest());
                log.info("Field 'type of rest' is set");
                break;

            case "price":
                String formPrice = tourModel.getPrice();
                if(!formPrice.isEmpty()){
                    tour.setPrice(Double.parseDouble(formPrice));
                    log.info("Field 'price' is set");
                }
                break;

            case "images":
                List<MultipartFile> formImages = tourModel.getImages();
                if (formImages != null) {
                    List<Image> images = tour.getImages();
                    String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
                    images.removeIf(image -> image.getUrl().equals(defaultImage));
                    for (MultipartFile formImage : formImages) {
                        Image uploadedHotelImage = imageService.upload(formImage, "tour", id);
                        images.add(uploadedHotelImage);
                    }
                    log.info("Field 'images' is set");
                }
                break;
        }
        tourRepository.save(tour);
    }
}
