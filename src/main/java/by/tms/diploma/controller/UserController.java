package by.tms.diploma.controller;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRegistrationModel;
import by.tms.diploma.service.UserService;
import by.tms.diploma.service.UserServiceImpl;
import by.tms.diploma.service.exception.AlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/authentication")
    public ModelAndView getAuthenticationView(ModelAndView modelAndView){
        modelAndView.setViewName("authentication");
        log.info("Authentication view (method Get)");
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationView(ModelAndView modelAndView){
        modelAndView.addObject("userForm", new UserRegistrationModel());
        modelAndView.setViewName("registration");
        log.info("Registration view (method Get)");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView postRegistrationView(@Valid @ModelAttribute("userForm")UserRegistrationModel userRegistrationModel,
                                             BindingResult bindingResult, ModelAndView modelAndView){
        if(!bindingResult.hasErrors()){
            if(!userService.isUsernameAlreadyExists(userRegistrationModel.getUsername())){
                if(!userService.isEmailAlreadyExists(userRegistrationModel.getEmail())){
                    User user = new User();
                    user.setUsername(userRegistrationModel.getUsername());
                    user.setPassword(userRegistrationModel.getPassword());
                    user.setName(userRegistrationModel.getName());
                    user.setEmail(userRegistrationModel.getEmail());
                    userService.save(user);
                }else {
                    modelAndView.addObject("emailExistError", "Email '"+
                            userRegistrationModel.getEmail()+"' is already exist!");
                }
            }else{
                modelAndView.addObject("usernameExistError", "Username '"+
                        userRegistrationModel.getUsername()+"' is already exist!");
            }
        }
        modelAndView.setViewName("registration");
        return modelAndView;
    }
}
