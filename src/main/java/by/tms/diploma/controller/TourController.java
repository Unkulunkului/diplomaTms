package by.tms.diploma.controller;


import by.tms.diploma.entity.*;
import by.tms.diploma.service.HotelService;
import by.tms.diploma.service.TourService;
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
@RequestMapping("/tour")
@Slf4j
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private HotelService hotelService;


    @GetMapping("/{id}")
    public ModelAndView getTourView(@PathVariable("id") long id, ModelAndView modelAndView){
        log.info("Request to open tour by id (id="+id+")");
        Optional<Tour> byId = tourService.findById(id);
        if (byId.isPresent()) {
            log.info("Tour with id="+id+" exists");
            Tour tour = byId.get();
            modelAndView.addObject("tour", tour);
        }else {
            log.info("Tour with id="+id+" doesn't exist");
            modelAndView.addObject("incorrectId", "Tour with id="+id+" doesn't exist!");
        }
        modelAndView.setViewName("tour");
        return modelAndView;
    }


    @GetMapping("/all/{pageNumber}")
    public ModelAndView getAllToursView(@PathVariable("pageNumber") int pageNumber, ModelAndView modelAndView){
        log.info("Request to get all tours");
        List<Tour> allTours = tourService.getAll(pageNumber);
        if (!allTours.isEmpty()) {
            log.info("List with hotels isn't empty");
            modelAndView.addObject("tours", allTours);
            modelAndView.addObject("pageNumber", pageNumber);
            modelAndView.addObject("tourFilterModel", new TourFilterModel());
        }else{
            log.info("List with tours is empty");
            modelAndView.addObject("emptyList", "Tour list is empty");
        }
        modelAndView.addObject("isFilter", false);
        modelAndView.setViewName("allTours");
        return modelAndView;
    }


    @GetMapping(path = "/add")
    public ModelAndView getAddView(ModelAndView modelAndView){
        log.info("Request to get add tour page");
        modelAndView.addObject("hotels", hotelService.findAll());
        modelAndView.addObject("tourAddForm", new TourAddModel());
        modelAndView.setViewName("addTour");
        return modelAndView;
    }

    @PostMapping(path = "/add")
    public ModelAndView postAddView(@Valid @ModelAttribute("tourAddForm") TourAddModel tourAddModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {
        log.info("Try to add tour");
        log.info("Add model: "+tourAddModel.toString());
        if(!bindingResult.hasErrors()){
            log.info("No binding result errors");
            String name = tourAddModel.getName();
            if(!tourService.existsByName(name)){
                log.info("Correct tour name");
                int tourDuration = Integer.parseInt(tourAddModel.getTourDuration());
                int dayAtSea = Integer.parseInt(tourAddModel.getDayAtSea());
                String result = tourService.validTourDurationAndDayAtSea(tourDuration, dayAtSea);
                if(result.equals("Ok")){
                    log.info("Value tour duration and day at sea is correct");
                    Tour tour = tourService.addModelToEntity(tourAddModel);
                    Tour savedTour = tourService.save(tour);
                    log.info("Tour "+savedTour+" has been saved");
                    modelAndView.setViewName("redirect:/tour/"+savedTour.getId());
                }else {
                    log.info("Value tour duration and day at sea isn't correct");
                    modelAndView.addObject("tourDurationAndDayAtSeaError", result);
                    modelAndView.setViewName("addTour");
                }
            }else {
                log.info("Tour with name '"+tourAddModel.getName()+" already exists");
                modelAndView.addObject("doesTourNameExist", true);
                modelAndView.setViewName("addTour");
            }
        }else {
            log.info("Binding result has errors");
            modelAndView.setViewName("addTour");
        }
        if(modelAndView.getViewName().equals("addTour")){
            modelAndView.addObject("hotels", hotelService.findAll());
        }
        return modelAndView;
    }

    @PostMapping(path = "/delete")
    public ModelAndView deleteTour(long id, ModelAndView modelAndView, RedirectAttributes redirectAttributes){
        log.info("Try to delete tour by id (id="+id+")");
        tourService.deleteById(id);
        redirectAttributes.addFlashAttribute("wasDeleted", true);
        log.info("Tour was deleted");
        modelAndView.setViewName("redirect:/tour/all");
        return modelAndView;
    }


    @GetMapping(path = "/edit")
    public ModelAndView editView(String nameOfEditableField, Long id, ModelAndView modelAndView){
        log.info("Request to get edit page");
        log.info("Edit parameters: name of editable field = "+nameOfEditableField+", id = "+id);
        if(id != null && tourService.existsById(id)){
            log.info("id != null and tour with this id exists");
            if(nameOfEditableField!= null){
                log.info("Name of editable field != null");
                Optional<Tour> optionalTour = tourService.findById(id);
                optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
                modelAndView.addObject("tourForm", new TourEditModel());
                modelAndView.addObject("nameOfEditableField", nameOfEditableField);
                modelAndView.addObject("hotels", hotelService.findAll());
            }else {
                log.info("Input editable field name is incorrect");
                modelAndView.addObject("incorrectField", "Input field is incorrect!");
            }
        }else{
            log.info("Input hotel id is incorrect");
            modelAndView.addObject("incorrectId", "Input id is incorrect!");
        }
        modelAndView.setViewName("editTour");
        return modelAndView;
    }

    @PostMapping(path = "/edit")
    public ModelAndView editTour (Long id, String nameOfEditableField, @Valid @ModelAttribute("tourForm")
            TourEditModel tourModel, BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        log.info("Try to edit tour");
        log.info("Edit parameters: name of editable field = "+nameOfEditableField+", id = "+id);
        log.info("Edit model: "+tourModel);
        if(!bindingResult.hasErrors()){
            log.info("No binding result errors");
            if(id != null){
                log.info("id != null");
                Optional<Tour> byId = tourService.findById(id);
                if(byId.isPresent()){
                    log.info("Tour with this id exists");
                    Tour tourFromBD = byId.get();
                    String result = "Ok";
                    if(nameOfEditableField.equals("tourDuration")){
                        log.info("Validation check tour duration and day at sea");
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
                    log.info("Result validation check: "+result);
                    if(result.equals("Ok")){
                        String name = tourModel.getName();
                        if(nameOfEditableField.equals("name") && tourService.existsByName(name)){
                            log.info("Tour with this name already exists");
                            modelAndView.addObject("doesTourNameExist", true);
                            modelAndView.setViewName("editTour");
                        }else {
                            tourService.updateFieldById(id, nameOfEditableField, tourModel);
                            log.info("Tour has been updated");
                            modelAndView.setViewName("redirect:/tour/"+ id);
                        }
                    }else {
                        modelAndView.addObject("tourDurationAndDayAtSeaError", result);
                        modelAndView.setViewName("editTour");
                    }
                }else {
                    log.info("Incorrect name of editable field");
                    modelAndView.addObject("nameOfEditableField", nameOfEditableField);
                    modelAndView.setViewName("redirect:/tour/edit");
                }
            }else {
                log.info("Incorrect id");
                modelAndView.addObject("incorrectId", "Input id is incorrect!");
                modelAndView.setViewName("redirect:/tour/edit");
            }
        }else{
            log.info("Binding result has errors");
            modelAndView.setViewName("editTour");
        }
        if(modelAndView.getViewName().equals("editTour")){
            Optional<Tour> optionalTour = tourService.findById(id);
            optionalTour.ifPresent(tour -> modelAndView.addObject("tour", tour));
            modelAndView.addObject("nameOfEditableField", nameOfEditableField);
            modelAndView.addObject("hotels", hotelService.findAll());
            modelAndView.addObject("id", id);
        }
        return modelAndView;
    }


    @PostMapping(path = "all/filter")
    public ModelAndView filterTour(@RequestParam int pageNumber, @Valid @ModelAttribute("tourFilterModel")
            TourFilterModel tourModel, BindingResult bindingResult, ModelAndView modelAndView){
        log.info("Try to filter tours. Page - "+pageNumber);
        log.info("Filter model: "+tourModel);
        if(!bindingResult.hasErrors()){
            List<Tour> tours = tourService.filterByPriceTourDurationDayAtSeaTypeOfRestAndHotel_Id(pageNumber,tourModel);
            if(!tours.isEmpty()){
                log.info("Filter result: "+tours);
                modelAndView.addObject("tours", tours);
            }else {
                log.info("Filter result: list is empty");
                modelAndView.addObject("emptyList", "Tour list is empty");
            }
        }
        modelAndView.addObject("isFilter", true);
        modelAndView.setViewName("allTours");
        return modelAndView;
    }
}
