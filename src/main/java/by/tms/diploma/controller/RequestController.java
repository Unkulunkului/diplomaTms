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
    public ModelAndView getClientRequestsView(ModelAndView modelAndView){
        modelAndView.addObject("list", requestStorageService.getAllRequest());
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
            clientRequest.setProgress(ClientRequestProgressEnum.WAITING);
            requestStorageService.addRequest(clientRequest);
            modelAndView.addObject("result", "Your request has been sent. Wait for our call :)");
        }
        modelAndView.setViewName("request");
        return modelAndView;
    }


    @PostMapping("/fromClients/setProgress")
    public ModelAndView postSetProcessingView(ClientRequestModel clientRequestModel, ModelAndView modelAndView){
        Optional<ClientRequest> requestId = requestStorageService.findById(clientRequestModel.getId());
        if (requestId.isPresent()) {
            ClientRequest clientRequest = requestId.get();
            clientRequest.setProgress(ClientRequestProgressEnum.valueOf(clientRequestModel.getProgress()));
        }
        modelAndView.setViewName("redirect:/request/fromClients");
        return modelAndView;
    }
}
