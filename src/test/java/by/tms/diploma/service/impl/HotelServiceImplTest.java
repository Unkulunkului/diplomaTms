package by.tms.diploma.service.impl;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class HotelServiceImplTest {

    @Autowired
    private HotelRepository repository;

    private Hotel hotel;
    private final String HOTEL_NAME = "Hotel";


    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setName(HOTEL_NAME);
    }

    @Test
    void existsByName() {
        repository.save(hotel);
        boolean actual = repository.existsByName(HOTEL_NAME);
        assertTrue(actual);
    }

    @Test
    void save() {
        Hotel expected = hotel;
        Hotel actual = repository.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void findByName() {
        Hotel expected = hotel;
        repository.save(expected);
        Hotel actual = repository.findByName(HOTEL_NAME);
        assertEquals(expected, actual);
    }

    @Test
    void getById() {
        Hotel expected = hotel;
        Hotel save = repository.save(expected);
        Hotel actual = repository.getById(save.getId());
        assertEquals(expected, actual);
    }

    @Test
    void existsById() {
        Hotel save = repository.save(hotel);
        long id = save.getId();
        boolean actual = repository.existsById(id);
        assertTrue(actual);
    }

    @Test
    void findById() {
        Hotel expected = hotel;
        Hotel save = repository.save(expected);
        long id = save.getId();
        Optional<Hotel> hotel = repository.findById(id);
        Hotel actual = new Hotel();
        if (hotel.isPresent()) {
            actual = hotel.get();
        }
        assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        Hotel secondHotel = new Hotel();
        Hotel thirdHotel = new Hotel();
        Hotel [] expected = new Hotel[3];
        expected[0] = (hotel);
        expected[1] = (secondHotel);
        expected[2] = (thirdHotel);
        List<Hotel> hotels = repository.saveAll(Arrays.asList(expected));
        Hotel[] actual = hotels.toArray(new Hotel[0]);
        assertArrayEquals(expected, actual);
    }

    @Test
    void updateFieldById() {
        Hotel save = repository.save(hotel);
        long id = save.getId();
        Hotel hotel = repository.getById(id);
        String expected = "New hotel";
        hotel.setName(expected);
        repository.save(hotel);
        hotel = repository.getById(id);
        String actual = hotel.getName();
        assertEquals(expected, actual);
    }
}