package by.tms.diploma.repository;

import by.tms.diploma.entity.Tour;
import by.tms.diploma.entity.TypeOfRest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long>{
    boolean existsByName(String name);
    Tour getById(long id);
    Page<Tour> getAllByPriceIsGreaterThanEqualAndPriceLessThanEqualAndTourDurationGreaterThanEqualAndDayAtSeaIsGreaterThanEqualAndTypeOfRestEqualsAndHotel_IdGreaterThanEqualAndHotel_IdLessThanEqual(
            double startPrice, double finishPrice, int startTourDuration, int startDayAtSea, TypeOfRest typeOfRest,
            long startId, long finishId, Pageable pageable);
    Optional<Tour> getByName(String name);
}
