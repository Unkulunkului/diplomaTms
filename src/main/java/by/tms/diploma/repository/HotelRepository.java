package by.tms.diploma.repository;

import by.tms.diploma.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByName(String name);
    Hotel findByName(String name);
}
