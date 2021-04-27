package by.tms.diploma.controller;



import by.tms.diploma.entity.Tour;
import by.tms.diploma.service.TourService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/wishes")
@Slf4j
public class WishesController {

    @Autowired
    private TourService tourService;

    @GetMapping
    public ModelAndView getWishesView(ModelAndView modelAndView){
        log.info("Request wishes page");
        modelAndView.setViewName("wishes");
        return modelAndView;
    }


    @PostMapping("/add")
    public ModelAndView postAddTour(long tourId, ModelAndView modelAndView, HttpSession httpSession,
                                    RedirectAttributes redirectAttributes){
        log.info("Try to add tour to wishes by tour id (id="+tourId+")");
        Tour tourFromDB = tourService.getById(tourId);
        List<Tour> wishes = (List<Tour>) httpSession.getAttribute("wishes");
        boolean isContains = false;
        for (Tour tour : wishes) {
            if (tour.getId()==tourFromDB.getId()) {
                isContains = true;
                break;
            }
        }
        if(!isContains){
            wishes.add(tourFromDB);
            log.info("Tour with id="+tourId+" was added to wishes");
        }else {
            log.info("Tour with id="+tourId+" already exists");
            redirectAttributes.addFlashAttribute("alreadyAdded", "This tour has already been added");
        }
        modelAndView.setViewName("redirect:/tour/"+tourId);
        return modelAndView;
    }

    @PostMapping(path = "remove/tour/{id}")
    public ModelAndView removeTour(@PathVariable("id") long id, ModelAndView modelAndView, HttpSession session,
                                   RedirectAttributes redirectAttributes){
        log.info("Try to remove tour with id="+id+" from wishes");
        List<Tour> wishes = (List<Tour>) session.getAttribute("wishes");
        boolean result = wishes.removeIf(tour -> tour.getId() == id);
        if(result){
            log.info("Tour with id="+id+" was removed from wishes");
            redirectAttributes.addFlashAttribute("remove", "Tour was removed from your wishes");
        }
        modelAndView.setViewName("redirect:/wishes");
        return modelAndView;
    }

}
