package by.tms.diploma.service;

import by.tms.diploma.entity.Hotel;

public interface HotelService {
    boolean existsByName(String hotelName);
    boolean existsById(long id);
    void add (Hotel hotel);
    Hotel findByName(String name);
    Hotel findById(long id);

}
