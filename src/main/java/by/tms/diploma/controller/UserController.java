package by.tms.diploma.controller;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRegistrationModel;
import by.tms.diploma.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping(path = "/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/authentication")
    public ModelAndView getAuthenticationView(ModelAndView modelAndView){
        log.info("Request authentication page");
        modelAndView.setViewName("authentication");
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView getRegistrationView(ModelAndView modelAndView){
        log.info("Request registration page");
        modelAndView.addObject("userForm", new UserRegistrationModel());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView postRegistrationView(@Valid @ModelAttribute("userForm")UserRegistrationModel userRegistrationModel,
                                             BindingResult bindingResult, ModelAndView modelAndView,
                                             RedirectAttributes redirectAttributes){
        log.info("User '"+userRegistrationModel.getUsername()+"' try to registration");
        if(!bindingResult.hasErrors()){
            log.info("No binding result errors");
            if(!userService.isUsernameExist(userRegistrationModel.getUsername())){
                if(!userService.isEmailExist(userRegistrationModel.getEmail())){
                    if(userRegistrationModel.getPassword().equals(userRegistrationModel.getConfirmPassword())){
                        log.info("Passwords match");
                        User user = new User();
                        user.setUsername(userRegistrationModel.getUsername());
                        user.setPassword(userRegistrationModel.getPassword());
                        user.setEmail(userRegistrationModel.getEmail());
                        userService.save(user);
                        log.info("User '"+user.getUsername()+"' has been registered");
                        redirectAttributes.addFlashAttribute("success", true);
                        modelAndView.setViewName("redirect:/user/authentication");
                    }else{
                        log.info("Passwords don't match");
                        modelAndView.addObject("differentPasswords", "Passwords don't match");
                        modelAndView.setViewName("registration");
                    }
                }else {
                    log.info("User with email '"+userRegistrationModel.getEmail()+ "' already exists");
                    modelAndView.addObject("emailExistError", "Email '"+
                            userRegistrationModel.getEmail()+"' is already exist!");
                    modelAndView.setViewName("registration");
                }
            }else{
                log.info("User with name '"+userRegistrationModel.getUsername()+ "' already exists");
                modelAndView.addObject("usernameExistError", "Username '"+
                        userRegistrationModel.getUsername()+"' is already exist!");
                modelAndView.setViewName("registration");
            }
        }else {
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }
}
