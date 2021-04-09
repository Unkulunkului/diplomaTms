package by.tms.diploma.controller;

import by.tms.diploma.entity.Basket;
import by.tms.diploma.entity.Tour;
import by.tms.diploma.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/basket")
@Slf4j
public class BasketController {


    @GetMapping
    public ModelAndView getBasketView(ModelAndView modelAndView){
        modelAndView.setViewName("basket");
        return modelAndView;
    }
}
