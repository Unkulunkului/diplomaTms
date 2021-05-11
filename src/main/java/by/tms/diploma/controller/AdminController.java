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

    @PostMapping("/findByUsername")
    public ModelAndView setModerator(String username, ModelAndView modelAndView){
        log.info("Find user by username");
        log.info("Check exist by username");
        if(userService.isUsernameExist(username)){
            log.info("User with username "+username+" exists!");
            if(userService.hasRoleByUsername(username, UserRole.MODERATOR)){
                log.info("User is already a moderator");
                modelAndView.addObject("alreadyModerator", "Already moderator!");
            }else {
                log.info("User isn't a moderator");
                User user = userService.getByUsername(username);
                modelAndView.addObject("userByUsername", user);
            }
        }else {
            log.info("User with username "+username+" doesn't exist!");
            modelAndView.addObject("userNotFound", "User not found");
        }
        modelAndView.addObject("listWithModerators", userService.getUsersByRole(UserRole.MODERATOR));
        modelAndView.setViewName("moderators");
        return modelAndView;
    }

    @PostMapping("/moderators/removeRole")
    public ModelAndView removeModerator(long id, ModelAndView modelAndView){
        log.info("Remove user role");
        if(userService.hasRoleById(id, UserRole.MODERATOR)){
            userService.removeRoleById(id, UserRole.MODERATOR);
        }
        modelAndView.setViewName("redirect:/admin/moderators");
        return modelAndView;
    }

    @PostMapping("/moderators/addRole")
    public ModelAndView addModerator(long id, ModelAndView modelAndView){
        log.info("Add user role");
        if(!userService.hasRoleById(id, UserRole.MODERATOR)){
            userService.addRoleById(id, UserRole.MODERATOR);
        }
        modelAndView.setViewName("redirect:/admin/moderators");
        return modelAndView;
    }
}
