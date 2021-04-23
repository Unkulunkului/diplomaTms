package by.tms.diploma.controller;

import by.tms.diploma.entity.*;
import by.tms.diploma.service.RequestService;
import by.tms.diploma.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(path = "/request")
@Slf4j
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserServiceImpl userService;


    @GetMapping(path = "/fromClients")
    public ModelAndView getClientRequestsView(ModelAndView modelAndView){
        modelAndView.addObject("list", requestService.getAllRequest());
        modelAndView.setViewName("clientRequests");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getRequestView(ModelAndView modelAndView){
        log.info("get");
        modelAndView.addObject("clientRequest", new ClientRequest());
        modelAndView.setViewName("request");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView postRequestView(@Valid @ModelAttribute("clientRequest") ClientRequest clientRequest,
                                        BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession,
                                        RedirectAttributes redirectAttributes){
        log.info(clientRequest.toString());
        if(!bindingResult.hasErrors()){
            List<Tour> wishes = (List<Tour>) httpSession.getAttribute("wishes");
            clientRequest.setTours(wishes);
            clientRequest.setRequestStatus(ClientRequestStatusEnum.WAITING);
            requestService.save(clientRequest);
            wishes.clear();
            httpSession.setAttribute("wishes", wishes);
            redirectAttributes.addFlashAttribute("result", "Your request has been sent. Wait for our call :)");
            modelAndView.setViewName("redirect:/tour/all");
        }else {
            modelAndView.setViewName("request");
        }
        return modelAndView;
    }


    @PostMapping("/fromClients/setStatus")
    public ModelAndView postSetStatus(ClientRequestModel clientRequestModel, ModelAndView modelAndView,
                                      HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
        long requestId = clientRequestModel.getId();
        Optional<ClientRequest> requestById = requestService.findById(requestId);
        Principal userPrincipal = httpServletRequest.getUserPrincipal();
        if (userPrincipal != null) {
            String curatorUsername = userPrincipal.getName();
            Optional<User> curatorByUsername = userService.getByUsername(curatorUsername);
            long curatorId = curatorByUsername.get().getId();
            ClientRequest clientRequest = requestById.get();
            if(!clientRequest.getRequestStatus().equals(ClientRequestStatusEnum.DONE)){
                ClientRequestStatusEnum status = ClientRequestStatusEnum.valueOf(clientRequestModel.getRequestStatus());
                switch (status){
                    case IN_PROGRESS:
                        requestService.updateStatusById(requestId, status);
                        requestService.setCuratorIdById(requestId, curatorId);
                        break;
                    case DONE:
                        if(!clientRequest.getRequestStatus().equals(ClientRequestStatusEnum.WAITING)){
                            List<UserRole> roles = curatorByUsername.get().getRoles();
                            long requestCuratorId = clientRequest.getCuratorId();
                            if(roles.contains(UserRole.ADMIN) || requestCuratorId == curatorId){
                                requestService.updateStatusById(requestId, status);
                            }else {
                                redirectAttributes.addFlashAttribute("isIncorrectCurator", true);
                            }
                        }else{
                            redirectAttributes.addFlashAttribute("isIncorrectStatus", true);
                        }
                        break;
                }
            }
        }
        modelAndView.setViewName("redirect:/request/fromClients");
        return modelAndView;
    }
}
