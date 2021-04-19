package by.tms.diploma.controller;


import by.tms.diploma.entity.*;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;
import by.tms.diploma.service.InMemoryCountryService;
import by.tms.diploma.service.TourService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/tour")
@Slf4j
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private InMemoryCountryService countryService;

    @Autowired
    private ImageService imageService;


    @GetMapping("/{id}")
    public ModelAndView getTourView(@PathVariable("id") long id, ModelAndView modelAndView){
        Optional<Tour> byId = tourService.findById(id);
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
            modelAndView.addObject("tourFilterModel", new TourFilterModel());
        }else{
            modelAndView.addObject("emptyList", "No tours");
        }
        modelAndView.setViewName("allTours");
        return modelAndView;
    }


    @PostMapping("/addToBasket")
    public ModelAndView postAddTour(long tourId, ModelAndView modelAndView, HttpSession httpSession){
        Tour tour = tourService.getById(tourId);
        List<Tour> basket = (List<Tour>) httpSession.getAttribute("basketWithTour");
        basket.add(tour);
        modelAndView.setViewName("redirect:"+tourId);
        return modelAndView;
    }


    @GetMapping(path = "/add")
    public ModelAndView getAddView(ModelAndView modelAndView){
        modelAndView.addObject("countries", countryService.getCountries());
        modelAndView.addObject("hotels", hotelService.findAll());
        modelAndView.addObject("tourAddForm", new TourAddModel());
        modelAndView.setViewName("addTour");
        return modelAndView;
    }

    @PostMapping(path = "/add")
    public ModelAndView postAddView(@Valid @ModelAttribute("tourAddForm") TourAddModel tourAddModel,
                                    BindingResult bindingResult, ModelAndView modelAndView,
                                    RedirectAttributes redirectAttributes) {
        if(!bindingResult.hasErrors()){
            String hotelName = tourAddModel.getHotelName();
            String name = tourAddModel.getName();
            if(!tourService.existsByName(name)){
                Hotel hotel = hotelService.findByName(hotelName);
                Tour tour = new Tour();
                tour.setHotel(hotel);
                tour.setName(name);
                tour.setDescription(tourAddModel.getDescription());
                tour.setDayAtSea(Integer.parseInt(tourAddModel.getDayAtSea()));
                tour.setTourDuration(Integer.parseInt(tourAddModel.getTourDuration()));
                tour.setPrice(Double.parseDouble(tourAddModel.getPrice()));
                tour.setVisitedCountries(tourAddModel.getVisitedCountries());
                tour.setTypeOfRest(TypeOfRest.getName(tourAddModel.getTypeOfRest()));
                Image image = new Image();
                image.getUrls().add("https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg");
                tour.setImages(image);
                tourService.save(tour);
                redirectAttributes.addFlashAttribute("createdTour", "Tour '"+name+"' was created!");
                modelAndView.setViewName("redirect:/tour/add");
            }else {
                modelAndView.addObject("countries", countryService.getCountries());
                modelAndView.addObject("hotels", hotelService.findAll());
                modelAndView.addObject("doesTourNameExist", true);
                modelAndView.setViewName("addTour");
            }
        }else {
            modelAndView.setViewName("addTour");
            modelAndView.addObject("countries", countryService.getCountries());
            modelAndView.addObject("hotels", hotelService.findAll());
        }
        return modelAndView;
    }

    @PostMapping(path = "/delete")
    public ModelAndView deleteTour(long id, ModelAndView modelAndView, RedirectAttributes redirectAttributes){
        tourService.deleteById(id);
        redirectAttributes.addFlashAttribute("wasDeleted", true);
        modelAndView.setViewName("redirect:/tour/all");
        return modelAndView;
    }

    @GetMapping(path = "/edit")
    public ModelAndView editView(long id, ModelAndView modelAndView){
        Optional<Tour> optionalTour = tourService.findById(id);
        optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
        modelAndView.addObject("tourForm", new TourAddModel());
        modelAndView.addObject("hotels", hotelService.findAll());
        modelAndView.addObject("countries", countryService.getCountries());
        modelAndView.setViewName("editTour");
        return modelAndView;
    }


    @PostMapping(path = "/edit")
    public ModelAndView editTour (long tourId, @Valid @ModelAttribute("tourForm") TourAddModel tourModel,
                                  BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        if(!bindingResult.hasErrors()){
            if(tourService.existsById(tourId)){
                if(!tourService.existsByName(tourModel.getName())){
                    Tour tour = new Tour();
                    tour.setId(tourId);
                    tour.setName(tourModel.getName());
                    Hotel hotelByName = hotelService.findByName(tourModel.getHotelName());
                    tour.setHotel(hotelByName);
                    tour.setDescription(tourModel.getDescription());
                    tour.setDayAtSea(Integer.parseInt(tourModel.getDayAtSea()));
                    tour.setTourDuration(Integer.parseInt(tourModel.getTourDuration()));
                    tour.setVisitedCountries(tourModel.getVisitedCountries());
                    tour.setTypeOfRest(TypeOfRest.getName(tourModel.getTypeOfRest()));
                    tour.setPrice(Double.parseDouble(tourModel.getPrice()));
                    List<MultipartFile> images = tourModel.getImages();
                    if(!images.get(0).isEmpty()){
                        Image image = imageService.upload(images, "tour", tour.getId());
                        tour.setImages(image);
                    }
                    tourService.update(tour);
                    modelAndView.setViewName("redirect:/tour/"+tourId);
                }else {
                    modelAndView.addObject("doesTourNameExist", true);
                    modelAndView.setViewName("editTour");
                }
            }else{
                modelAndView.addObject("doesTourByIdExist", true);
                modelAndView.setViewName("editTour");
            }
        }else{
            modelAndView.setViewName("editTour");
        }
        if(modelAndView.getViewName().equals("editTour")){
            modelAndView.addObject("hotels", hotelService.findAll());
            modelAndView.addObject("countries", countryService.getCountries());
            Optional<Tour> optionalTour = tourService.findById(tourId);
            optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
        }
        return modelAndView;
    }

    @PostMapping(path = "/filter")
    public ModelAndView filterTour(TourFilterModel tourModel, ModelAndView modelAndView){
        double startPrice = Double.parseDouble(tourModel.getStartPrice());
        double finishPrice = Double.parseDouble(tourModel.getFinishPrice());
        int dayAtSea = tourModel.getDayAtSea();
        int tourDuration = tourModel.getTourDuration();
        TypeOfRest type = TypeOfRest.getName(tourModel.getTypeOfRest());
        List<Tour> tours = tourService.filterByPriceTourDurationDayAtSeaTypeOfRest(startPrice, finishPrice,
                tourDuration, dayAtSea, type);
        modelAndView.addObject("tours", tours);
        modelAndView.addObject("filter", true);
        modelAndView.setViewName("allTours");
        return modelAndView;
    }
}
