package by.tms.diploma.controller;

import by.tms.diploma.entity.*;

import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private ImageService imageService;



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
                                         BindingResult bindingResult, ModelAndView modelAndView,
                                         RedirectAttributes redirectAttributes) {
        if(!bindingResult.hasErrors()){
            if(!hotelService.existsByName(hotelAddModel.getName())){
                Hotel hotel = new Hotel();
                hotel.setStars(Integer.parseInt(hotelAddModel.getStars()));
                hotel.setCountry(hotelAddModel.getCountry());
                hotel.setName(hotelAddModel.getName());
                hotel.setDescription(hotelAddModel.getDescription());
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
    public ModelAndView editView(Long id, ModelAndView modelAndView){

        if(id != null && hotelService.existsById(id)){
            Optional<Hotel> optionalHotel = hotelService.findById(id);
            optionalHotel.ifPresent(hotel -> modelAndView.addObject("hotel", hotel));
            modelAndView.addObject("hotelForm", new HotelAddModel());
        }else {
            modelAndView.addObject("incorrectId", "Input id is incorrect!");
        }

        modelAndView.setViewName("editHotel");
        return modelAndView;
    }


    @PostMapping(path = "/edit")
    public ModelAndView editTour (long hotelId, @Valid @ModelAttribute("hotelForm") HotelAddModel hotelModel,
                                  BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        if(!bindingResult.hasErrors()){
            if(!hotelService.existsByName(hotelModel.getName())){
                Hotel hotel = new Hotel();
                hotel.setLineFromTheSea(Integer.parseInt(hotelModel.getLineFromTheSea()));
                hotel.setDescription(hotelModel.getDescription());
                hotel.setCountry(hotelModel.getCountry());
                hotel.setStars(Integer.parseInt(hotelModel.getStars()));
                hotel.setName(hotelModel.getName());
                hotel.setId(hotelId);
                List<MultipartFile> images = hotelModel.getImages();
                if(!images.get(0).isEmpty()){
                    Image hotelImage = imageService.upload(images, "hotel", hotelId);
                    hotel.setImages(hotelImage);
                }
                hotelService.update(hotel);
                modelAndView.setViewName("redirect:/hotel/"+hotelId);
            }else {
                modelAndView.addObject("doesHotelNameExist", true);
                modelAndView.setViewName("editHotel");
            }
        }else{
            modelAndView.setViewName("editHotel");
        }
        if(modelAndView.getViewName().equals("editHotel")){
            Optional<Hotel> optionalHotel = hotelService.findById(hotelId);
            optionalHotel.ifPresent(hotel -> modelAndView.addObject("hotel", hotel));
        }
        return modelAndView;
    }
}
