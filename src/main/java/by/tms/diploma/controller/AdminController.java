package by.tms.diploma.controller;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;
import by.tms.diploma.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(path = "/admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/moderators")
    public ModelAndView getModeratorsView(ModelAndView modelAndView){
        log.info("Request to display page with moderator list");
        modelAndView.addObject("listWithModerators", userService.getUsersByRole(UserRole.MODERATOR));
        modelAndView.setViewName("moderators");
        return modelAndView;
    }

    @PostMapping("/findUserByUsername")
    public ModelAndView postSetModerator(String username, ModelAndView modelAndView){
        log.info("Find user by username");
        Optional<User> byUsername = userService.getByUsername(username);
        log.info("Check exist by username");
        if(byUsername.isPresent()){
            log.info("User with username "+username+" exists!");
            User user = byUsername.get();
            if(!user.getRoles().contains(UserRole.MODERATOR)){
                log.info("User isn't a moderator");
                modelAndView.addObject("userByUsername", user);
            }else {
                log.info("User is already a moderator");
                modelAndView.addObject("alreadyModerator", "Already moderator!");
            }
        }else {
            log.info("User with username "+username+" doesn't exist!");
            modelAndView.addObject("userNotFound", "User not found");
        }
        modelAndView.addObject("listWithModerators", userService.getUsersByRole(UserRole.MODERATOR));
        modelAndView.setViewName("moderators");
        return modelAndView;
    }

    @PostMapping("/moderators/updateRole")
    public ModelAndView postRemoveModerator(long id, ModelAndView modelAndView){
        log.info("Change user role");
        userService.updateRoleById(id, UserRole.MODERATOR);
        modelAndView.setViewName("redirect:/admin/moderators");
        return modelAndView;
    }


}
