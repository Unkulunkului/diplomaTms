package by.tms.diploma.controller;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.HotelAddModel;

import by.tms.diploma.service.HotelService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping(path = "/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/add")
    public ModelAndView getHotelAddView(ModelAndView modelAndView){
        modelAndView.addObject("hotelAddForm", new HotelAddModel());
        modelAndView.setViewName("addHotel");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView postHotelAddView(@Valid @ModelAttribute("hotelAddForm") HotelAddModel hotelAddModel,
                                      BindingResult bindingResult, ModelAndView modelAndView){
        if(!bindingResult.hasErrors()){
            if(!hotelService.existsByName(hotelAddModel.getName())){
                Hotel hotel = new Hotel();
                hotel.setStars(hotelAddModel.getStars());
                hotel.setCountry(hotelAddModel.getCountry());
                hotel.setName(hotelAddModel.getName());
                hotel.setDescription(hotelAddModel.getDescription());
                hotel.setImages(hotelAddModel.getImages());
                hotel.setPets(hotelAddModel.isPets());
                hotelService.add(hotel);
            }else {
                modelAndView.addObject("hotelExistError","Hotel '"+hotelAddModel.getName()+
                        "' is already exist!");
            }
        }

        modelAndView.setViewName("addHotel");
        return modelAndView;
    }
}
