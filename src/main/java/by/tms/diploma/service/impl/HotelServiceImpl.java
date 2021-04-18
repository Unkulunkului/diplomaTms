package by.tms.diploma.service.impl;

import by.tms.diploma.entity.*;
import by.tms.diploma.repository.HotelRepository;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {


    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ImageService imageService;

    @Override
    public boolean existsByName(String hotelName){
        return hotelRepository.existsByName(hotelName);
    }

    @Override
    public void add(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name);
    }

    @Override
    public Hotel getById(long id) {
        return hotelRepository.getById(id);
    }

    @Override
    public boolean existsById(long id){
        return hotelRepository.existsById(id);
    }

    @Override
    public Optional<Hotel> findById(long id){
        return hotelRepository.findById(id);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }
    @Override
    public List<Hotel> findAllByParams(String name, String country, int startStar, int finishStar){
        return hotelRepository.findAllByNameEqualsOrCountryEqualsAndStarsGreaterThanEqualAndStarsLessThanEqual(
                name, country, startStar, finishStar);
    }

    @Override
    public void update(Hotel hotel){
        long id = hotel.getId();
        Hotel hotelFromBD = hotelRepository.getById(id);
        Image hotelFromBDImages = hotelFromBD.getImages();
        List<String> imageBDHotelUrls = hotelFromBDImages.getUrls();
        String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
        imageBDHotelUrls.remove(defaultImage);
        List<String> formImageUrls = hotel.getImages().getUrls();
        imageBDHotelUrls.addAll(formImageUrls);
        hotelRepository.save(hotel);
        log.info(hotelFromBD.toString());
    }
}
