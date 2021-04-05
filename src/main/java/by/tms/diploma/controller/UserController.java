package by.tms.diploma.controller;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRegistrationModel;
import by.tms.diploma.service.UserService;
import by.tms.diploma.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/authorization")
    public ModelAndView getAuthorizationView(ModelAndView modelAndView){
        modelAndView.setViewName("authorization");
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationView(ModelAndView modelAndView){
        modelAndView.addObject("userForm", new UserRegistrationModel());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView postRegistrationView(@Valid @ModelAttribute("userForm")UserRegistrationModel userRegistrationModel,
                                             BindingResult bindingResult, ModelAndView modelAndView){
        if(!bindingResult.hasErrors()){
            if(!userService.isUsernameAlreadyExists(userRegistrationModel.getUsername())){
                if(!userService.isEmailAlreadyExists(userRegistrationModel.getEmail())){
                    User user = new User(); //Where can I do transfer? Here or in service?
                    user.setUsername(userRegistrationModel.getUsername());
                    user.setPassword(userRegistrationModel.getPassword());
                    user.setName(userRegistrationModel.getName());
                    user.setEmail(userRegistrationModel.getEmail());
                    userService.save(user);
                }else {
                    modelAndView.addObject("emailAlreadyExist", "Email '"+
                            userRegistrationModel.getEmail()+"' is already exist!");
                }
            }else{
                modelAndView.addObject("usernameAlreadyExist", "Username '"+
                        userRegistrationModel.getUsername()+"' is already exist!");
            }
        }
        modelAndView.setViewName("registration");
        return modelAndView;
    }
}
