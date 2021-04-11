package by.tms.diploma.controller;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
