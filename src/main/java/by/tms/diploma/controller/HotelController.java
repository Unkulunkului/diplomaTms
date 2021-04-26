package by.tms.diploma.controller;

import by.tms.diploma.entity.*;

import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.io.IOException;
import java.util.*;

@Controller
@Slf4j
@RequestMapping(path = "/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private WeatherService weatherService;



    @GetMapping("/{id}")
    public ModelAndView getHotelView(@PathVariable("id") long id, ModelAndView modelAndView){
        Optional<Hotel> byId = hotelService.findById(id);
        if (byId.isPresent()) {
            Hotel hotel = byId.get();
            log.info(hotel.toString());
            Weather weather = weatherService.getWeather(hotel.getCountry().getCity());
            if(weather!=null){
                modelAndView.addObject("weather", weather);
            }else {
                modelAndView.addObject("weatherCityError", "Incorrect city");
            }

            modelAndView.addObject("hotel", hotel);
        }else {
            modelAndView.addObject("incorrectId", "Hotel with id="+id+" doesn't exist!");
        }
        modelAndView.setViewName("hotel");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllToursView(ModelAndView modelAndView){
        List<Hotel> allHotels = hotelService.findAll();
        if (!allHotels.isEmpty()) {
            modelAndView.addObject("hotels", allHotels);
        }else{
            modelAndView.addObject("emptyList", "Hotel list is empty");
        }
        modelAndView.setViewName("allHotels");
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
                                         BindingResult bindingResult, ModelAndView modelAndView,
                                         RedirectAttributes redirectAttributes) {
        if(!bindingResult.hasErrors()){
            if(!hotelService.existsByName(hotelAddModel.getName())){
                Hotel hotel = new Hotel();
                hotel.setStars(Integer.parseInt(hotelAddModel.getStars()));
                String formCity = hotelAddModel.getCity();
                String formCountry = hotelAddModel.getCountry();
                Country country = new Country();
                country.setCity(formCity);
                country.setName(formCountry);
                hotel.setCountry(country);
                hotel.setName(hotelAddModel.getName());
                hotel.setDescription(hotelAddModel.getDescription());
                hotel.setTypeOfFood(hotelAddModel.getTypeOfFood());
                hotel.setLineFromTheSea(Integer.parseInt(hotelAddModel.getLineFromTheSea()));
                Image image = new Image();
                image.getUrls().add("https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg");
                hotel.setImages(image);
                redirectAttributes.addFlashAttribute("createdHotel", "Hotel '"+hotel.getName()+
                        "' was created!");
                hotelService.add(hotel);
                modelAndView.setViewName("redirect:/hotel/add");
            }else {
                modelAndView.addObject("doesHotelExist",true);
                modelAndView.setViewName("addHotel");
            }
        }else{
            modelAndView.setViewName("addHotel");
        }
        return modelAndView;
    }


    @GetMapping(path = "/edit")
    public ModelAndView editView(String nameOfEditableField, Long id, ModelAndView modelAndView){
        if(nameOfEditableField!= null){
            if(id != null && hotelService.existsById(id)){
                Optional<Hotel> optionalHotel = hotelService.findById(id);
                optionalHotel.ifPresent(hotel -> modelAndView.addObject("hotel", hotel));
                modelAndView.addObject("hotelForm", new HotelEditModel());
                modelAndView.addObject("nameOfEditableField", nameOfEditableField);
            }else {
                modelAndView.addObject("incorrectId", "Input id is incorrect!");
            }
        }else{
            modelAndView.addObject("incorrectField", "Input field is incorrect!");
        }
        modelAndView.setViewName("editHotel");
        return modelAndView;
    }

    @PostMapping(path = "/edit")
    public ModelAndView editTour (long id, String nameOfEditableField, @Valid @ModelAttribute("hotelForm") HotelEditModel hotelModel,
                                  BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        log.info(id+"");
        log.info(hotelModel.toString());
        log.info(nameOfEditableField);
        if(!bindingResult.hasErrors()){
            String name = hotelModel.getName();
            if(nameOfEditableField.equals("name") && hotelService.existsByName(name)){
                log.info("name ex");
                modelAndView.addObject("doesHotelNameExist", true);
                modelAndView.setViewName("editHotel");
            }else {
                hotelService.updateFieldById(id, nameOfEditableField, hotelModel);
                log.info("update");
                modelAndView.setViewName("redirect:/hotel/"+ id);
            }
        }else{
            log.info("binding");
            modelAndView.setViewName("editHotel");
        }
        if(modelAndView.getViewName().equals("editHotel")){
            Optional<Hotel> optionalHotel = hotelService.findById(id);
            optionalHotel.ifPresent(hotel -> modelAndView.addObject("hotel", hotel));
            modelAndView.addObject("nameOfEditableField", nameOfEditableField);
            modelAndView.addObject("id", id);
        }
        return modelAndView;
    }
}
