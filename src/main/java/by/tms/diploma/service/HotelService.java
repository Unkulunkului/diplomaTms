package by.tms.diploma.service;

import by.tms.diploma.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HotelService {
    boolean existsByName(String hotelName);
    boolean existsById(long id);
    Hotel save(Hotel hotel);
    Hotel findByName(String name);
    Hotel getById(long id);
    Optional<Hotel> findById(long id);
    List<Hotel> findAll();
    void updateFieldById(long id, String fieldName, HotelEditModel hotelModel) throws IOException;
}
