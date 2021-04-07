package by.tms.diploma.controller;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.Tour;
import by.tms.diploma.entity.TourAddModel;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private HotelService hotelService;



    @GetMapping(path = "/add")
    public ModelAndView getAddView(ModelAndView modelAndView){
        modelAndView.setViewName("addTour");
        modelAndView.addObject("tourAddForm", new TourAddModel());
        return modelAndView;
    }

    @PostMapping(path = "/add")
    public ModelAndView postAddView(@Valid @ModelAttribute("tourAddForm") TourAddModel tourAddModel,
                                    BindingResult bindingResult, ModelAndView modelAndView){
        if(!bindingResult.hasErrors()){
            String hotelName = tourAddModel.getHotelName();
            if (hotelService.existsByName(hotelName)) {
                String name = tourAddModel.getName();
                if(!tourService.existsByName(name)){
                    Hotel byName = hotelService.findByName(hotelName);
                    Tour tour = new Tour();
                    tour.setHotel(byName);
                    tour.setName(name);
                    tour.setDescription(tourAddModel.getDescription());
                    tour.setCountry(tourAddModel.getCountry());
                    tour.setPrice(Double.parseDouble(tourAddModel.getPrice()));
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
        modelAndView.setViewName("addTour");
        return modelAndView;
    }
}
