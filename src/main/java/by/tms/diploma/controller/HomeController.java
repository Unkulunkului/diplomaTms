package by.tms.diploma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/")
public class HomeController {

    @GetMapping
    public ModelAndView getHomeView(ModelAndView modelAndView){
        String text = "Just buy our tours";
        modelAndView.addObject("text", text);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
