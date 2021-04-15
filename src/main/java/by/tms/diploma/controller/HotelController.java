package by.tms.diploma.controller;

import by.tms.diploma.entity.*;

import by.tms.diploma.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(path = "/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{id}")
    public ModelAndView getHotelView(@PathVariable("id") long id, ModelAndView modelAndView){
        Optional<Hotel> byId = hotelService.findById(id);
        if (byId.isPresent()) {
            Hotel hotel = byId.get();
            modelAndView.addObject("hotel", hotel);
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
                                         BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession){
        if(!bindingResult.hasErrors()){
            if(!hotelService.existsByName(hotelAddModel.getName())){
                Hotel hotel = new Hotel();
                hotel.setStars(Integer.parseInt(hotelAddModel.getStars()));
                hotel.setCountry(hotelAddModel.getCountry());
                hotel.setName(hotelAddModel.getName());
                hotel.setDescription(hotelAddModel.getDescription());
                hotel.setImages(hotelAddModel.getImages());
                hotel.setPets(hotelAddModel.isPets());
                httpSession.setAttribute("hotelForm", hotel);
                modelAndView.setViewName("addHotelRoom");
                modelAndView.addObject("hotelRoomForm", new HotelRoom());
            }else {
                modelAndView.addObject("doesHotelExist",true);
                modelAndView.setViewName("addHotel");
            }
        }else{
            modelAndView.setViewName("addHotel");
        }
        return modelAndView;
    }

    @PostMapping("add/addRoom")
    public ModelAndView postAddRoomView(@ModelAttribute("hotelRoomForm") HotelRoom room, ModelAndView modelAndView,
                                        HttpSession httpSession, RedirectAttributes redirectAttributes){
        Hotel hotel = (Hotel) httpSession.getAttribute("hotelForm");
        hotel.setHotelRoom(room);
        hotelService.add(hotel);
        log.info(hotel.toString());
        redirectAttributes.addFlashAttribute("createdHotel", "Hotel '"+hotel.getName()+
                "' was created!");
        modelAndView.setViewName("redirect:/hotel/add");
        return modelAndView;
    }

    @GetMapping(path = "filter")
    public ModelAndView getHotels(ModelAndView modelAndView){
        modelAndView.setViewName("hotelFilter");
        return modelAndView;
    }

    @PostMapping(path = "filter")
    public ModelAndView postHotels(String country, String name, int startStar, int finishStar,
                                   ModelAndView modelAndView){
        if(name.trim().isEmpty() && country.trim().isEmpty()){
            modelAndView.addObject("emptyInput", "Enter country or hotel name");
        }else{
            List<Hotel> allByParams = hotelService.findAllByParams(name, country, startStar, finishStar);
            if(!allByParams.isEmpty()){
                modelAndView.addObject("hotels", allByParams);
            }else {
                modelAndView.addObject("emptyListHotels", "There are no hotels that match your " +
                        "request");
            }

        }
        modelAndView.setViewName("hotelFilter");
        return modelAndView;
    }
}
