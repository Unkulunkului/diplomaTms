package by.tms.diploma.service.impl;

import by.tms.diploma.entity.*;

import by.tms.diploma.repository.TourRepository;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TourServiceImplTest {

    @MockBean
    private TourRepository tourRepository;

    @Autowired
    private TourServiceImpl tourService;

    private Tour tour;

    private final long FIRST_TOUR_ID = 1L;
    private final long SECOND_TOUR_ID = 2L;
    private final long THIRD_TOUR_ID = 3L;

    private final String FIRST_TOUR_NAME = "First tour";
    private final String SECOND_TOUR_NAME = "Second tour";
    private final String THIRD_TOUR_NAME = "Third tour";

    private final TypeOfRest FIRST_TOUR_TYPE_OF_REST = TypeOfRest.EXCURSION_TOUR;
    private final TypeOfRest SECOND_TOUR_TYPE_OF_REST = TypeOfRest.EXCURSION_TOUR;
    private final TypeOfRest THIRD_TOUR_TYPE_OF_REST = TypeOfRest.EXTREME_TOUR;

    private final int FIRST_TOUR_DAY_AT_SEA = 3;
    private final int SECOND_TOUR_DAY_AT_SEA = 4;
    private final int THIRD_TOUR_DAY_AT_SEA = 5;

    private final int VALID_TOUR_DAY_AT_SEA = 5;
    private final int VALID_TOUR_DURATION = 11;
    private final String VALIDATION_RESULT = "Ok";

    private final String NAME_OF_EDITABLE_FIELD = "name";
    private final String NEW_NAME_VALUE = "New name";


    @BeforeEach
    void setUp() {
        tour = new Tour();
        tour.setTypeOfRest(FIRST_TOUR_TYPE_OF_REST);
        tour.setDayAtSea(FIRST_TOUR_DAY_AT_SEA);
        tour.setName(FIRST_TOUR_NAME);
        tour.setId(FIRST_TOUR_ID);

    }

    @Test
    void save() {
        Mockito.when(tourRepository.save(tour)).thenReturn(tour);
        Tour actual = tourService.save(tour);
        assertEquals(tour, actual);
    }

    @Test
    void existsByName() {
        Mockito.when(tourRepository.existsByName(FIRST_TOUR_NAME)).thenReturn(true);
        boolean actual = tourService.existsByName(FIRST_TOUR_NAME);
        assertTrue(actual);
    }

    @Test
    void existsById() {
        Mockito.when(tourRepository.existsById(FIRST_TOUR_ID)).thenReturn(true);
        boolean actual = tourService.existsById(FIRST_TOUR_ID);
        assertTrue(actual);
    }

    @Test
    void findById() {
        Mockito.when(tourRepository.findById(FIRST_TOUR_ID)).thenReturn(Optional.of(tour));
        Optional<Tour> byId = tourService.findById(FIRST_TOUR_ID);
        Tour tourFromRepository = new Tour();
        if(byId.isPresent()){
            tourFromRepository = byId.get();
        }
        assertEquals(tour, tourFromRepository);
    }

    @Test
    void getAll() {
        Tour secondTour = new Tour();
        secondTour.setName(SECOND_TOUR_NAME);
        secondTour.setId(SECOND_TOUR_ID);

        Tour thirdTour = new Tour();
        thirdTour.setName(THIRD_TOUR_NAME);
        thirdTour.setId(THIRD_TOUR_ID);

        List<Tour> tours = new ArrayList<>();
        tours.add(thirdTour);
        tours.add(secondTour);
        tours.add(tour);

        Mockito.when(tourRepository.findAll()).thenReturn(tours);

        List<Tour> allFromRepository = tourService.getAll();

        assertEquals(tours.size(), allFromRepository.size());
    }

    @Test
    void getById() {
        Mockito.when(tourRepository.getById(FIRST_TOUR_ID)).thenReturn(tour);
        Tour tourFromRepository = tourService.getById(FIRST_TOUR_ID);
        assertEquals(tour ,tourFromRepository);
    }

    @Test
    void filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id() {
        Tour secondTour = new Tour();
        secondTour.setName(SECOND_TOUR_NAME);
        secondTour.setId(SECOND_TOUR_ID);
        secondTour.setTypeOfRest(SECOND_TOUR_TYPE_OF_REST);
        secondTour.setDayAtSea(SECOND_TOUR_DAY_AT_SEA);

        List<Tour> tours = new ArrayList<>();
        tours.add(secondTour);
        tours.add(tour);

        double startPrice = 0;
        double finishPrice = 999999.99d;
        int startTourDuration = 0;
        int startDayAtSea = 2;
        long startId = 0;
        long finishId = Long.MAX_VALUE;
        TypeOfRest typeOfRest = TypeOfRest.EXCURSION_TOUR;

        Mockito.when(tourRepository.getAllByPriceIsGreaterThanEqualAndPriceLessThanEqualAndTourDurationGreaterThanEqualAndDayAtSeaIsGreaterThanEqualAndTypeOfRestEqualsAndHotel_IdGreaterThanEqualAndHotel_IdLessThanEqual(
                startPrice, finishPrice, startTourDuration, startDayAtSea, typeOfRest, startId, finishId)
        ).thenReturn(tours);

        TourFilterModel tourFilterModel = new TourFilterModel();
        tourFilterModel.setDayAtSea(startDayAtSea+"");
        tourFilterModel.setTypeOfRest("Excursion tour");

        List<Tour> toursFromRepository = tourService.filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id(tourFilterModel);
        assertArrayEquals(tours.toArray(), toursFromRepository.toArray());
    }

    @Test
    void validTourDurationAndDayAtSea() {
        String actual = tourService.validTourDurationAndDayAtSea(VALID_TOUR_DURATION, VALID_TOUR_DAY_AT_SEA);
        assertEquals(VALIDATION_RESULT, actual);
    }

    @Test
    @SneakyThrows
    void updateFieldById() {
        Mockito.when(tourRepository.getById(FIRST_TOUR_ID)).thenReturn(tour);
        Mockito.spy(tourRepository).save(tour);
        TourEditModel editModel = new TourEditModel();
        editModel.setName(NEW_NAME_VALUE);

        tourService.updateFieldById(FIRST_TOUR_ID, NAME_OF_EDITABLE_FIELD, editModel);
        Tour actual = tourService.getById(FIRST_TOUR_ID);
        Tour expected = new Tour();
        expected.setName(NEW_NAME_VALUE);
        expected.setId(FIRST_TOUR_ID);
        expected.setTypeOfRest(FIRST_TOUR_TYPE_OF_REST);
        expected.setDayAtSea(FIRST_TOUR_DAY_AT_SEA);
        assertEquals(expected, actual);
    }
}