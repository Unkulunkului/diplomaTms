package by.tms.diploma.service.impl;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import by.tms.diploma.entity.TourUpdateModel;
import by.tms.diploma.repository.TourRepository;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private HotelService hotelService;

    @Override
    public void save(Tour tour) {
        tourRepository.save(tour);
    }

    @Override
    public boolean existsByName(String name) {
        return tourRepository.existsByName(name);
    }

    @Override
    public boolean existsById(long id) {
        return tourRepository.existsById(id);
    }

    @Override
    public Optional<Tour> getById(long id) {
        return tourRepository.findById(id);
    }

    @Override
    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        tourRepository.deleteById(id);
    }

    @Override
    public void updateTour(TourUpdateModel tourModel) {
        Tour tour = tourRepository.findById(tourModel.getId()).get();
        tour.setName(tourModel.getName());
        tour.setPricePerDay(Double.parseDouble(tourModel.getPricePerDay()));
        Hotel hotelByName = hotelService.findByName(tourModel.getHotelName());
        tour.setCountry(hotelByName.getCountry());
        tour.setHotel(hotelByName);
        tour.setDescription(tourModel.getDescription());
        tourRepository.save(tour);
    }


}
