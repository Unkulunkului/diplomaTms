package by.tms.diploma.service;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService{
    @Autowired
    private HotelRepository hotelRepository;

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
    public Hotel findById(long id){
        Hotel byId = hotelRepository.getOne(id);
        return byId;
    }
}
