package by.tms.diploma.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@Controller
@RequestMapping(path = "/")
@Slf4j
public class HomeController {


    @GetMapping
    public ModelAndView getHomeView(ModelAndView modelAndView){
        log.info("Request to open home page");
        modelAndView.setViewName("home");
        return modelAndView;
    }


}
