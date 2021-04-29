package by.tms.diploma.service.impl;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.HotelEditModel;
import by.tms.diploma.repository.HotelRepository;
import by.tms.diploma.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HotelServiceImplTest {

    @Autowired
    private HotelService hotelService = new HotelServiceImpl();

    @MockBean
    private HotelRepository hotelRepository;


    private Hotel firstHotel;
    private Hotel secondHotel;
    private final String FIRST_HOTEL_NAME = "First hotel name";
    private final long FIRST_HOTEL_ID = 1L;
    private final String SECOND_HOTEL_NAME = "Secont hotel name";
    private final long SECOND_HOTEL_ID = 1L;


    @BeforeEach
    void setUp() {
        firstHotel = new Hotel();
        firstHotel.setId(FIRST_HOTEL_ID);
        firstHotel.setName(FIRST_HOTEL_NAME);

        secondHotel = new Hotel();
        secondHotel.setId(SECOND_HOTEL_ID);
        secondHotel.setName(SECOND_HOTEL_NAME);
    }

    @Test
    void existsByName() {
        Mockito.when(hotelRepository.existsByName(firstHotel.getName())).thenReturn(true);
        boolean actual = hotelService.existsByName(FIRST_HOTEL_NAME);
        assertTrue(actual);
    }

    @Test
    void save() {
        Mockito.when(hotelRepository.save(Mockito.any(Hotel.class))).thenReturn(firstHotel);
        Hotel actual = hotelService.save(firstHotel);
        assertEquals(firstHotel, actual);
    }

    @Test
    void findByName() {
        Mockito.when(hotelRepository.findByName(firstHotel.getName())).thenReturn(firstHotel);
        Hotel actual = hotelRepository.findByName(FIRST_HOTEL_NAME);
        assertEquals(firstHotel, actual);
    }

    @Test
    void getById() {
        Mockito.when(hotelRepository.getById(FIRST_HOTEL_ID)).thenReturn(firstHotel);
        Hotel actual = hotelService.getById(FIRST_HOTEL_ID);
        assertEquals(firstHotel, actual);
    }

    @Test
    void existsById() {
        Mockito.when(hotelRepository.existsById(FIRST_HOTEL_ID)).thenReturn(true);
        boolean actual = hotelRepository.existsById(FIRST_HOTEL_ID);
        assertTrue(actual);
    }

    @Test
    void findById() {
        Mockito.when(hotelRepository.findById(FIRST_HOTEL_ID)).thenReturn(Optional.of(firstHotel));
        Optional<Hotel> fromRepository = hotelService.findById(FIRST_HOTEL_ID);
        Hotel actual = new Hotel();
        if (fromRepository.isPresent()) {
            actual = fromRepository.get();
        }
        assertEquals(firstHotel, actual);
    }

    @Test
    void findAll() {
        Mockito.when(hotelRepository.findAll()).thenReturn(Arrays.asList(firstHotel, secondHotel));
        List<Hotel> listFromRepository = hotelService.findAll();
        assertEquals(2, listFromRepository.size());

    }

//    @Test
//    void updateFieldById(){
//        Hotel expected = new Hotel();
//        expected.setId(1L);
//        expected.setName("New name");
//        Mockito.when(hotelRepository.getById(FIRST_HOTEL_ID)).thenReturn(firstHotel);
//        Mockito.when(hotelRepository.save(Mockito.any(Hotel.class))).thenReturn(expected);
//        HotelEditModel editModel = new HotelEditModel();
//        editModel.setName("New name");
//        assertDoesNotThrow(hotelService.updateFieldById(1L, "name", editModel));
//    }
}