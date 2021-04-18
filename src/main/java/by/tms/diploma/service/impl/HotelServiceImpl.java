package by.tms.diploma.service.impl;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Image;
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
    public void updateImages(long id, List<MultipartFile> images) throws IOException {
        Hotel hotel = hotelRepository.findById(id).get();
        Image hotelImages = hotel.getImages();
        List<String> urls = hotelImages.getUrls();
        String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
        urls.remove(defaultImage);
        Image image = imageService.upload(images, "hotel", id);
        urls.addAll(image.getUrls());
        hotelRepository.save(hotel);
    }
}
