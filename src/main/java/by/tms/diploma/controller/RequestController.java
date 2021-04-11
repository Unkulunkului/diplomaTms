package by.tms.diploma.controller;

import by.tms.diploma.entity.*;
import by.tms.diploma.service.RequestStorageService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(path = "/request")
@Slf4j
public class RequestController {

    @Autowired
    private RequestStorageService requestStorageService;

    @Autowired
    private UserServiceImpl userService;


    @GetMapping(path = "/fromClients")
    public ModelAndView getClientRequestsView(boolean isIncorrectCurator, ModelAndView modelAndView){
        modelAndView.addObject("list", requestStorageService.getAllRequest());
        modelAndView.addObject("isIncorrectCurator",isIncorrectCurator);
        modelAndView.setViewName("clientRequests");
        return modelAndView;
    }

    @GetMapping
    public ModelAndView getRequestView(ModelAndView modelAndView){
        modelAndView.addObject("clientRequest", new ClientRequest());
        modelAndView.setViewName("request");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView postRequestView(@Valid @ModelAttribute("clientRequest") ClientRequest clientRequest,
                                        BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession){
        if(!bindingResult.hasErrors()){
            List<Tour> basketWithTour = (List<Tour>) httpSession.getAttribute("basketWithTour");
            clientRequest.setTours(basketWithTour);
            clientRequest.setRequestStatus(ClientRequestStatusEnum.WAITING);
            requestStorageService.addRequest(clientRequest);
            modelAndView.addObject("result", "Your request has been sent. Wait for our call :)");
        }
        modelAndView.setViewName("request");
        return modelAndView;
    }


    @PostMapping("/fromClients/setStatus")
    public ModelAndView postSetStatus(ClientRequestModel clientRequestModel, ModelAndView modelAndView,
                                      HttpServletRequest httpServletRequest){
        long requestId = clientRequestModel.getId();
        Optional<ClientRequest> requestById = requestStorageService.findById(requestId);

        String curatorUsername = httpServletRequest.getUserPrincipal().getName();
        Optional<User> curatorByUsername = userService.getByUsername(curatorUsername);
        long curatorId = curatorByUsername.get().getId();

        ClientRequest clientRequest = requestById.get();
        if(!clientRequest.getRequestStatus().equals(ClientRequestStatusEnum.DONE)){
            switch (ClientRequestStatusEnum.valueOf(clientRequestModel.getRequestStatus())){
                case IN_PROGRESS:
                    clientRequest.setRequestStatus(ClientRequestStatusEnum.IN_PROGRESS);
                    clientRequest.setCuratorId(curatorId);
                    break;
                case DONE:
                    List<UserRole> roles = curatorByUsername.get().getRoles();
                    long requestCuratorId = clientRequest.getCuratorId();
                    if(roles.contains(UserRole.ADMIN) || requestCuratorId == curatorId){
                        clientRequest.setRequestStatus(ClientRequestStatusEnum.DONE);
                    }else {
                        modelAndView.addObject("isIncorrectCurator", true);
                    }
                    break;
            }
        }
        modelAndView.setViewName("redirect:/request/fromClients");
        return modelAndView;
    }
}
