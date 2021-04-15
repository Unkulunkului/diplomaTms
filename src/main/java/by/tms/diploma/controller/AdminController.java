package by.tms.diploma.controller;

import by.tms.diploma.entity.User;
import by.tms.diploma.entity.UserRole;
import by.tms.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/moderators")
    public ModelAndView getModeratorsView(ModelAndView modelAndView){
        modelAndView.addObject("listWithModerators", userService.getUsersByRole(UserRole.MODERATOR));
        modelAndView.setViewName("moderators");
        return modelAndView;
    }

    @PostMapping("/findUserByUsername")
    public ModelAndView postSetModerator(String username, ModelAndView modelAndView){
        Optional<User> byUsername = userService.getByUsername(username);
        if(byUsername.isPresent()){
            User user = byUsername.get();
            if(!user.getRoles().contains(UserRole.MODERATOR)){
                modelAndView.addObject("userByUsername", user);
            }else {
                modelAndView.addObject("alreadyModerator", true);
            }
        }else {
            modelAndView.addObject("userNotFound", true);
        }
        modelAndView.addObject("listWithModerators", userService.getUsersByRole(UserRole.MODERATOR));
        modelAndView.setViewName("moderators");
        return modelAndView;
    }

    @PostMapping("/moderators/updateRole")
    public ModelAndView postRemoveModerator(long id, ModelAndView modelAndView){
        userService.updateRoleById(id, UserRole.MODERATOR);
        modelAndView.setViewName("redirect:/admin/moderators");
        return modelAndView;
    }


}
