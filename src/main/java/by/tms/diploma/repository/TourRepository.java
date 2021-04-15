package by.tms.diploma.repository;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    boolean existsByName(String name);
}
