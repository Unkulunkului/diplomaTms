package by.tms.diploma.service;

import by.tms.diploma.entity.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    boolean existsByName(String hotelName);
    boolean existsById(long id);
    void add (Hotel hotel);
    Hotel findByName(String name);
    Optional<Hotel> findById(long id);
    List<Hotel> findAll();

}
