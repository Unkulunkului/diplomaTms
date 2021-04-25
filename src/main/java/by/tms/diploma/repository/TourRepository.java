package by.tms.diploma.repository;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import by.tms.diploma.entity.TypeOfRest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {
    boolean existsByName(String name);
    Tour getById(long id);
    List<Tour> getAllByPriceIsGreaterThanEqualAndPriceLessThanEqualAndTourDurationGreaterThanEqualAndDayAtSeaIsGreaterThanEqualAndTypeOfRestEquals(
            double startPrice, double finishPrice, int startTourDuration, int startDayAtSea, TypeOfRest typeOfRest);
    Optional<Tour> getByName(String name);
}
