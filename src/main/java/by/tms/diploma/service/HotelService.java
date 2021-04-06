package by.tms.diploma.service;

import by.tms.diploma.entity.Hotel;

public interface HotelService {
    boolean existsByName(String hotelName);
    void add (Hotel hotel);
    Hotel findByName(String name);
}
