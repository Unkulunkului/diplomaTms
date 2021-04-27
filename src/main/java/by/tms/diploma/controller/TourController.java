package by.tms.diploma.controller;


import by.tms.diploma.entity.*;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.ImageService;

import by.tms.diploma.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
            modelAndView.addObject("emptyList", "Tour list is empty");
        }
        modelAndView.setViewName("allTours");
        return modelAndView;
    }


    @PostMapping("/addToWishes")
    public ModelAndView postAddTour(long tourId, ModelAndView modelAndView, HttpSession httpSession,
                                    RedirectAttributes redirectAttributes){
        Tour tour = tourService.getById(tourId);
        List<Tour> basket = (List<Tour>) httpSession.getAttribute("wishes");
        if(!basket.contains(tour)){
            basket.add(tour);
        }else {
            redirectAttributes.addFlashAttribute("alreadyAdded", "This tour has already been added");
        }
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
                                    BindingResult bindingResult, ModelAndView modelAndView) {
        if(!bindingResult.hasErrors()){
            String hotelName = tourAddModel.getHotelName();
            String name = tourAddModel.getName();
            if(!tourService.existsByName(name)){
                int tourDuration = Integer.parseInt(tourAddModel.getTourDuration());
                int dayAtSea = Integer.parseInt(tourAddModel.getDayAtSea());
                String result = tourService.validTourDurationAndDayAtSea(tourDuration, dayAtSea);
                if(result.equals("Ok")){
                    Hotel hotel = hotelService.findByName(hotelName);
                    Tour tour = new Tour();
                    tour.setHotel(hotel);
                    tour.setName(name);
                    tour.setDescription(tourAddModel.getDescription());
                    tour.setDayAtSea(dayAtSea);
                    tour.setTourDuration(tourDuration);
                    tour.setPrice(Double.parseDouble(tourAddModel.getPrice()));
                    tour.setVisitedCountries(tourAddModel.getVisitedCountries());
                    tour.setTypeOfRest(TypeOfRest.getEnumName(tourAddModel.getTypeOfRest()));
                    Image image = new Image();
                    image.setUrl("https://timeoutcomputers.com.au/wp-content/uploads/2016/12/noimage.jpg");
                    List<Image> images = new ArrayList<>();
                    images.add(image);
                    tour.setImages(images);
                    Tour savedTour = tourService.save(tour);
                    modelAndView.setViewName("redirect:/tour/"+savedTour.getId());
                }else {
                    modelAndView.addObject("tourDurationAndDayAtSeaError", result);
                    modelAndView.setViewName("addTour");
                }
            }else {
                modelAndView.addObject("doesTourNameExist", true);
                modelAndView.setViewName("addTour");
            }
        }else {
            modelAndView.setViewName("addTour");
        }
        if(modelAndView.getViewName().equals("addTour")){
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
    public ModelAndView editView(String nameOfEditableField, Long id, ModelAndView modelAndView){
        if(id != null && tourService.existsById(id)){
            if(nameOfEditableField!= null){
                Optional<Tour> optionalTour = tourService.findById(id);
                optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
                modelAndView.addObject("tourForm", new TourEditModel());
                modelAndView.addObject("nameOfEditableField", nameOfEditableField);
                modelAndView.addObject("hotels", hotelService.findAll());
            }else {
                modelAndView.addObject("incorrectField", "Input field is incorrect!");
            }
        }else{
            modelAndView.addObject("incorrectId", "Input id is incorrect!");
        }
        modelAndView.setViewName("editTour");
        return modelAndView;
    }

    @PostMapping(path = "/edit")
    public ModelAndView editTour (Long id, String nameOfEditableField, @Valid @ModelAttribute("tourForm")
            TourEditModel tourModel, BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        log.info(id+"-"+nameOfEditableField);
        log.info(tourModel.toString());
        if(!bindingResult.hasErrors()){
            log.info("binding no err");
            if(id != null){
                log.info("id != null");
                Optional<Tour> byId = tourService.findById(id);
                if(byId.isPresent()){
                    log.info("Tour by id exist");
                    Tour tourFromBD = byId.get();
                    String result = "Ok";

                    if(nameOfEditableField.equals("tourDuration")){
                        log.info("tour dur try change");
                        int dayAtSea;
                        int tourDuration;
                        String formTourDuration = tourModel.getTourDuration();
                        if(!formTourDuration.isEmpty()){
                            tourDuration = Integer.parseInt(formTourDuration);
                        }else {
                            tourDuration = tourFromBD.getTourDuration();
                        }
                        String formDayAtSea = tourModel.getDayAtSea();
                        if(!formDayAtSea.isEmpty()){
                            dayAtSea = Integer.parseInt(formDayAtSea);
                        }else {
                            dayAtSea = tourFromBD.getDayAtSea();
                        }
                        result = tourService.validTourDurationAndDayAtSea(tourDuration, dayAtSea);
                    }

                    if(result.equals("Ok")){
                        log.info("valid tour success");
                        String name = tourModel.getName();
                        if(nameOfEditableField.equals("name") && tourService.existsByName(name)){
                            log.info("name ex");
                            modelAndView.addObject("doesTourNameExist", true);
                            modelAndView.setViewName("editTour");
                        }else {
                            tourService.updateFieldById(id, nameOfEditableField, tourModel);
                            log.info("update");
                            modelAndView.setViewName("redirect:/tour/"+ id);
                        }
                    }else {
                        modelAndView.addObject("tourDurationAndDayAtSeaError", result);
                        modelAndView.setViewName("editTour");
                    }
                }else {
                    modelAndView.addObject("nameOfEditableField", nameOfEditableField);
                    modelAndView.setViewName("redirect:/tour/edit");
                }
            }else {
                modelAndView.addObject("nameOfEditableField", nameOfEditableField);
                modelAndView.setViewName("redirect:/tour/edit");
            }
        }else{
            log.info("binding");
            modelAndView.setViewName("editTour");
        }
        if(modelAndView.getViewName().equals("editTour")){
            Optional<Tour> optionalTour = tourService.findById(id);
            optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
            modelAndView.addObject("nameOfEditableField", nameOfEditableField);
            modelAndView.addObject("hotels", hotelService.findAll());
            modelAndView.addObject("id", id);//???????
        }
        return modelAndView;
    }

//    @GetMapping(path = "/edit")
//    public ModelAndView editView(Long id, ModelAndView modelAndView){
//        log.info(id+"");
//        if(id != null && tourService.existsById(id)){
//            Optional<Tour> optionalTour = tourService.findById(id);
//            optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
//            modelAndView.addObject("tourForm", new TourEditModel());
//            modelAndView.addObject("hotels", hotelService.findAll());
//        }else {
//            modelAndView.addObject("incorrectId", "Input id is incorrect!");
//        }
//        modelAndView.setViewName("editTour");
//        return modelAndView;
//    }
//
//
//    @PostMapping(path = "/edit")
//    public ModelAndView editTour (long tourId, @Valid @ModelAttribute("tourForm") TourEditModel tourModel,
//                                  BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
//        if(!bindingResult.hasErrors()){
//            if(tourService.theSameTour(tourId, tourModel.getName()) || !tourService.existsByName(tourModel.getName())){
//                int dayAtSea = Integer.parseInt(tourModel.getDayAtSea());
//                int tourDuration = Integer.parseInt(tourModel.getTourDuration());
//                String result = tourService.validTourDurationAndDayAtSea(tourDuration, dayAtSea);
//                if(result.equals("Ok")){
//                    Tour tour = new Tour();
//                    tour.setId(tourId);
//                    tour.setName(tourModel.getName());
//                    Hotel hotelByName = hotelService.findByName(tourModel.getHotelName());
//                    tour.setHotel(hotelByName);
//                    tour.setDescription(tourModel.getDescription());
//                    tour.setDayAtSea(dayAtSea);
//                    tour.setTourDuration(tourDuration);
//                    tour.setVisitedCountries(tourModel.getVisitedCountries());
//                    tour.setTypeOfRest(TypeOfRest.getName(tourModel.getTypeOfRest()));
//                    tour.setPrice(Double.parseDouble(tourModel.getPrice()));
//                    List<MultipartFile> images = tourModel.getImages();
//                    if(images != null){
//                        Image image = imageService.upload(images, "tour", tour.getId());
//                        tour.setImages(image);
//                    }
//                    tourService.update(tour);
//                    modelAndView.setViewName("redirect:/tour/"+tourId);
//                }else {
//                    modelAndView.addObject("tourDurationAndDayAtSeaError", result);
//                    modelAndView.setViewName("editTour");
//                }
//            }else {
//                modelAndView.addObject("doesTourNameExist", true);
//                modelAndView.setViewName("editTour");
//            }
//        }else{
//            modelAndView.setViewName("editTour");
//        }
//        if(modelAndView.getViewName().equals("editTour")){
//            modelAndView.addObject("hotels", hotelService.findAll());
//            Optional<Tour> optionalTour = tourService.findById(tourId);
//            optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
//        }
//        return modelAndView;
//    }

    @PostMapping(path = "/filter")
    public ModelAndView filterTour(@Valid @ModelAttribute("tourFilterModel") TourFilterModel tourModel,
                                   BindingResult bindingResult, ModelAndView modelAndView){
        if(!bindingResult.hasErrors()){
            double startPrice = 0;
            double finishPrice = 999999.99d;
            int startTourDuration = 0;
            int startDayAtSea = 0;
            long startId = 0;
            long finishId = 0;
            String hotelName = tourModel.getHotelName();
            if(!hotelName.isEmpty()){
                if (hotelService.existsByName(hotelName)) {
                    Hotel byName = hotelService.findByName(hotelName);
                    startId = byName.getId();
                    finishId = byName.getId();
                }
            }else {
                finishId = Long.MAX_VALUE;
            }
            if(!tourModel.getStartPrice().isEmpty()){
                startPrice = Double.parseDouble(tourModel.getStartPrice());
            }
            if(!tourModel.getFinishPrice().isEmpty()){
                finishPrice = Double.parseDouble(tourModel.getFinishPrice());
            }
            if(!tourModel.getTourDuration().isEmpty()){
                startTourDuration = Integer.parseInt(tourModel.getTourDuration());
            }
            if(!tourModel.getDayAtSea().isEmpty()){
                startDayAtSea = Integer.parseInt(tourModel.getDayAtSea());
            }
            TypeOfRest type = TypeOfRest.getEnumName(tourModel.getTypeOfRest());
            List<Tour> tours = tourService.filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id(startPrice, finishPrice,
                    startTourDuration, startDayAtSea, type, startId, finishId);
            if(!tours.isEmpty()){
                modelAndView.addObject("tours", tours);
            }else {
                modelAndView.addObject("emptyList", "Tour list is empty");
            }
        }
        modelAndView.setViewName("allTours");
        return modelAndView;
    }
}
