package by.tms.diploma.service;

import by.tms.diploma.entity.Tour;
import by.tms.diploma.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourServiceImpl implements TourService {

    @Autowired
    private TourRepository tourRepository;

    @Override
    public void add(Tour tour) {
        tourRepository.save(tour);
    }

    @Override
    public boolean existsByName(String name) {
        return tourRepository.existsByName(name);
    }
}
