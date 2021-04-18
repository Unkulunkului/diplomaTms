package by.tms.diploma.repository;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.ws.Holder;
import java.util.List;


public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByName(String name);
    Hotel findByName(String name);
    Hotel getById(long id);
    List<Hotel> findAllByNameEqualsOrCountryEqualsAndStarsGreaterThanEqualAndStarsLessThanEqual(
            String name, String country, int startStar, int finishStar);
}
