package by.tms.diploma.controller;


import by.tms.diploma.entity.Tour;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(path = "/wishes")
@Slf4j
public class WishesController {

    @GetMapping
    public ModelAndView getWishesView(ModelAndView modelAndView){
        modelAndView.setViewName("wishes");
        return modelAndView;
    }

//    @GetMapping(path = "delete/{id}")
//    public ModelAndView deleteTour(@PathVariable("id") long id, ModelAndView modelAndView, HttpSession session){
//        List<Tour> basket = (List<Tour>) session.getAttribute("basketWithTour");
//        for (Tour tour : basket) {
//            if (tour.getId()==id) {
//                basket.remove(tour);
//            }
//        }
//        modelAndView.setViewName("basket");
//        return modelAndView;
//    }

}
