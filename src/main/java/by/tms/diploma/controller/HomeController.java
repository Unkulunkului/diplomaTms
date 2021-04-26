package by.tms.diploma.controller;

import by.tms.diploma.entity.Tour;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping(path = "/")
@Slf4j
public class HomeController {


    @GetMapping
    public ModelAndView getHomeView(ModelAndView modelAndView){
        modelAndView.setViewName("home");
        return modelAndView;
    }


}
