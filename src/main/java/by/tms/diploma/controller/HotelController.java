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
        log.info("Request to open hotel by id (id="+id+")");
        Optional<Hotel> byId = hotelService.findById(id);
        if (byId.isPresent()) {
            log.info("Hotel with id="+id+" exists");
            Hotel hotel = byId.get();
            log.info(hotel.toString());
            Weather weather = weatherService.getWeather(hotel.getCountry().getCity());
            if(weather!=null){
                log.info("Weather has been determined");
                log.info(weather.toString());
                modelAndView.addObject("weather", weather);
            }else {
                log.warn("Weather has been determined. Incorrect city");
                modelAndView.addObject("weatherCityError", "Incorrect city");
            }
            modelAndView.addObject("hotel", hotel);
        }else {
            log.info("Hotel with id="+id+" doesn't exist");
            modelAndView.addObject("incorrectId", "Hotel with id="+id+" doesn't exist!");
        }
        modelAndView.setViewName("hotel");
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllHotelsView(ModelAndView modelAndView){
        log.info("Request to get all hotels");
        List<Hotel> allHotels = hotelService.findAll();
        if (!allHotels.isEmpty()) {
            log.info("List with hotels isn't empty");
            modelAndView.addObject("hotels", allHotels);
        }else{
            log.info("List with hotels is empty");
            modelAndView.addObject("emptyList", "Hotel list is empty");
        }
        modelAndView.setViewName("allHotels");
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView getHotelAddView(ModelAndView modelAndView){
        log.info("Request to get add hotel page");
        modelAndView.addObject("hotelAddForm", new HotelAddModel());
        modelAndView.setViewName("addHotel");
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView postHotelAddView(@Valid @ModelAttribute("hotelAddForm") HotelAddModel hotelAddModel,
                                         BindingResult bindingResult, ModelAndView modelAndView,
                                         RedirectAttributes redirectAttributes) {
        log.info("Try to add hotel");
        log.info("Add model: "+hotelAddModel.toString());
        if(!bindingResult.hasErrors()){
            log.info("No binding result errors");
            if(!hotelService.existsByName(hotelAddModel.getName())){
                log.info("Correct hotel name");
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
                List<Image> images = new ArrayList<>();
                Image image = new Image();
                images.add(image);
                image.setUrl("https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg");
                hotel.setImages(images);
                redirectAttributes.addFlashAttribute("createdHotel", "Hotel '"+hotel.getName()+
                        "' was created!");
                Hotel save = hotelService.save(hotel);
                log.info("Hotel "+save+" was saved");
                modelAndView.setViewName("redirect:/hotel/add");
            }else {
                log.info("Hotel with name '"+hotelAddModel.getName()+" already exists");
                modelAndView.addObject("doesHotelExist",true);
                modelAndView.setViewName("addHotel");
            }
        }else{
            log.info("Binding result has errors");
            modelAndView.setViewName("addHotel");
        }
        return modelAndView;
    }


    @GetMapping(path = "/edit")
    public ModelAndView editView(String nameOfEditableField, Long id, ModelAndView modelAndView){
        log.info("Request to get edit page");
        log.info("Edit parameters: name of editable field = "+nameOfEditableField+", id = "+id);
        if(nameOfEditableField!= null){
            log.info("Name of editable field != null");
            if(id != null && hotelService.existsById(id)){
                log.info("id != null and hotel with this id exists");
                Optional<Hotel> optionalHotel = hotelService.findById(id);
                optionalHotel.ifPresent(hotel -> modelAndView.addObject("hotel", hotel));
                modelAndView.addObject("hotelForm", new HotelEditModel());
                modelAndView.addObject("nameOfEditableField", nameOfEditableField);
            }else {
                log.info("Input hotel id is incorrect");
                modelAndView.addObject("incorrectId", "Input id is incorrect!");
            }
        }else{
            log.info("Input editable field name is incorrect");
            modelAndView.addObject("incorrectField", "Input field is incorrect!");
        }
        modelAndView.setViewName("editHotel");
        return modelAndView;
    }

    @PostMapping(path = "/edit")
    public ModelAndView editHotel (long id, String nameOfEditableField, @Valid @ModelAttribute("hotelForm") HotelEditModel hotelModel,
                                  BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        log.info("Try to edit hotel");
        log.info("Edit parameters: name of editable field = "+nameOfEditableField+", id = "+id);
        log.info("Edit model: "+hotelModel);
        if(!bindingResult.hasErrors()){
            log.info("No binding result errors");
            String name = hotelModel.getName();
            if(nameOfEditableField.equals("name") && hotelService.existsByName(name)){
                log.info("Input name already exists");
                modelAndView.addObject("doesHotelNameExist", true);
                modelAndView.setViewName("editHotel");
            }else {
                hotelService.updateFieldById(id, nameOfEditableField, hotelModel);
                log.info("Update hotel field was success");
                modelAndView.setViewName("redirect:/hotel/"+ id);
            }
        }else{
            log.info("Binding result has errors");
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
