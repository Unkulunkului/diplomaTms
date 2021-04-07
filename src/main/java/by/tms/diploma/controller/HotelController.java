package by.tms.diploma.controller;

import by.tms.diploma.entity.Hotel;
import by.tms.diploma.entity.HotelAddModel;

import by.tms.diploma.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping(path = "/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{id}")
    public ModelAndView getHotelView(@PathVariable("id") long id, ModelAndView modelAndView){
        if (hotelService.existsById(id)) {
            Hotel byId = hotelService.findById(id);
            modelAndView.addObject("hotel", byId);
        }else {
            modelAndView.addObject("incorrectId", "Hotel with id="+id+" doesn't exist!");
        }
        modelAndView.setViewName("hotel");
        return modelAndView;
    }

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
                hotel.setStars(Integer.parseInt(hotelAddModel.getStars()));
                hotel.setCountry(hotelAddModel.getCountry());
                hotel.setName(hotelAddModel.getName());
                hotel.setDescription(hotelAddModel.getDescription());
                hotel.setImages(hotelAddModel.getImages());
                hotel.setPets(hotelAddModel.isPets());
                hotelService.add(hotel);
                modelAndView.addObject("createdHotel", "Tour '"+hotelAddModel.getName()+
                        "' was created!");
            }else {
                modelAndView.addObject("doesHotelExist",true);
            }
        }

        modelAndView.setViewName("addHotel");
        return modelAndView;
    }
}
