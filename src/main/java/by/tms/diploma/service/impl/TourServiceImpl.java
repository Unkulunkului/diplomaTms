package by.tms.diploma.service.impl;

import by.tms.diploma.entity.Tour;
import by.tms.diploma.repository.TourRepository;
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

    @Override
    public void add(Tour tour) {
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


    public List<Tour>getAllFilter(Tour example){
        return tourRepository.findAll(Example.of(example));
    }

}
