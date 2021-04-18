package by.tms.diploma.service;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HotelService {
    boolean existsByName(String hotelName);
    boolean existsById(long id);
    void add (Hotel hotel);
    Hotel findByName(String name);
    Optional<Hotel> findById(long id);
    List<Hotel> findAll();
    List<Hotel> findAllByParams(String name, String country, int startStar, int finishStar);
    void updateImages(long id, List<MultipartFile> images) throws IOException;
}
