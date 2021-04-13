package by.tms.diploma.controller;


import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import by.tms.diploma.entity.TourAddModel;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tour")
@Slf4j
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private HotelService hotelService;


    @GetMapping("/{id}")
    public ModelAndView getTourView(@PathVariable("id") long id, ModelAndView modelAndView){
        Optional<Tour> byId = tourService.getById(id);
        if (byId.isPresent()) {
            Tour tour = byId.get();
            modelAndView.addObject("tour", tour);
        }else {
            modelAndView.addObject("incorrectId", "Tour with id="+id+" doesn't exist!");
        }
        modelAndView.setViewName("tour");
        return modelAndView;
    }


    @GetMapping("/all")
    public ModelAndView getAllToursView(ModelAndView modelAndView){
        List<Tour> allTours = tourService.getAll();
        if (!allTours.isEmpty()) {
            modelAndView.addObject("tours", allTours);
        }else{
            modelAndView.addObject("emptyList", "No tours");
        }
        modelAndView.setViewName("allTours");
        return modelAndView;
    }


    @PostMapping("/addToBasket")
    public ModelAndView postAddTour(long tourId, ModelAndView modelAndView, HttpSession httpSession){
        Tour tour = tourService.getById(tourId).get();
        List<Tour> basket = (List<Tour>) httpSession.getAttribute("basketWithTour");
        basket.add(tour);
        modelAndView.setViewName("redirect:"+tourId);
        return modelAndView;
    }


    @GetMapping(path = "/add")
    public ModelAndView getAddView(ModelAndView modelAndView){
        modelAndView.addObject("hotels", hotelService.findAll());
        modelAndView.addObject("tourAddForm", new TourAddModel());
        modelAndView.setViewName("addTour");
        return modelAndView;
    }

    @PostMapping(path = "/add")
    public ModelAndView postAddView(@Valid @ModelAttribute("tourAddForm") TourAddModel tourAddModel,
                                    BindingResult bindingResult, ModelAndView modelAndView){
        if(!bindingResult.hasErrors()){
            String hotelName = tourAddModel.getHotelName();
            if (hotelName !=null) {
                log.info("after hotel != null");
                String name = tourAddModel.getName();
                if(!tourService.existsByName(name)){
                    Hotel hotel = hotelService.findByName(hotelName);
                    Tour tour = new Tour();
                    tour.setHotel(hotel);
                    tour.setName(name);
                    tour.setDescription(tourAddModel.getDescription());
                    tour.setCountry(hotel.getCountry());
                    tour.setPricePerDay(Double.parseDouble(tourAddModel.getPricePerDay()));
                    tour.setImages(tourAddModel.getImages());
                    tourService.add(tour);
                    modelAndView.addObject("createdTour", "Tour '"+name+"' was created!");
                }else {
                    modelAndView.addObject("doesTourNameExist", true);
                }
            }else {
                modelAndView.addObject("doesHotelNotExist", true);
            }
        }
        modelAndView.addObject("hotels", hotelService.findAll());
        modelAndView.setViewName("addTour");
        return modelAndView;
    }
}
