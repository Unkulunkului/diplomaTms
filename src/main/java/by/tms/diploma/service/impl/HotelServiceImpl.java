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
        boolean result = hotelRepository.existsByName(hotelName);
        log.info("Hotel exist by hotel name ("+hotelName+"): "+result);
        return result;
    }

    @Override
    public Hotel save(Hotel hotel) {
        log.info("Save hotel: "+hotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel findByName(String name) {
        log.info("Find hotel by name(name = "+name+")");
        return hotelRepository.findByName(name);
    }

    @Override
    public Hotel getById(long id) {
        log.info("Find hotel by id(id = "+id+")");
        return hotelRepository.getById(id);
    }

    @Override
    public boolean existsById(long id){
        log.info("Exist hotel by id(id = "+id+")");
        return hotelRepository.existsById(id);
    }

    @Override
    public Optional<Hotel> findById(long id){
        log.info("Find hotel by id(id = "+id+")");
        return hotelRepository.findById(id);
    }

    @Override
    public List<Hotel> findAll() {
        log.info("Find all hotels");
        return hotelRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Override
    public void updateFieldById(long id, String fieldName, HotelEditModel hotelModel) throws IOException {
        log.info("Update hotel field("+fieldName+") by id(id = "+id+")");
        log.info("Hotel update model: "+hotelModel.toString());
        Hotel hotel = hotelRepository.getById(id);
        switch (fieldName) {
            case "name":
                String formName = hotelModel.getName();
                if(!formName.isEmpty()){
                    hotel.setName(formName);
                    log.info("Field 'name' is set");
                }
                String stars = hotelModel.getStars();
                if(!stars.isEmpty()) {
                    hotel.setStars(Integer.parseInt(stars));
                    log.info("Field 'stars' is set");
                }
                break;
            case "country":
                String formCity = hotelModel.getCity();
                String formCountry = hotelModel.getCountry();
                if(!formCity.isEmpty() && !formCountry.isEmpty()){
                    Country country = new Country();
                    country.setCity(formCity);
                    country.setName(formCountry);
                    hotel.setCountry(country);
                    log.info("Field 'country' is set");
                }
                break;
            case "lineFromTheSea":
                String formLineFromTheSea = hotelModel.getLineFromTheSea();
                log.info(formLineFromTheSea);
                if(!formLineFromTheSea.isEmpty()){
                    hotel.setLineFromTheSea(Integer.parseInt(formLineFromTheSea));
                    log.info("Field 'line from the sea' is set");
                }
                break;
            case "typeOfFood":
                log.info(hotelModel.getTypeOfFood().name());
                hotel.setTypeOfFood(hotelModel.getTypeOfFood());
                log.info("Field 'type of food' is set");
                break;
            case "description":
                hotel.setDescription(hotelModel.getDescription());
                log.info("Field 'description' is set");
                break;
            case "images":
                List<MultipartFile> formImages = hotelModel.getImages();
                if (formImages != null) {
                    List<Image> images = hotel.getImages();
                    String defaultImage = "https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg";
                    images.removeIf(image -> image.getUrl().equals(defaultImage));
                    for (MultipartFile formImage : formImages) {
                        Image uploadedHotelImage = imageService.upload(formImage, "hotel", id);
                        images.add(uploadedHotelImage);
                    }
                    log.info("Field 'images' is set");
                }
                break;
        }
        hotelRepository.save(hotel);
    }
}
