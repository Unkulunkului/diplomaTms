package by.tms.diploma.repository;

import by.tms.diploma.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByName(String name);
    Hotel findByName(String name);
    List<Hotel> findAllByNameEqualsOrCountryEqualsAndStarsGreaterThanEqualAndStarsLessThanEqual(
            String name, String country, int startStar, int finishStar);
}
