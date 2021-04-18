package by.tms.diploma.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler
    public String ioException(IOException ex, Model model){
        model.addAttribute("error", "Oops, something wrong!");
        log.error(ex.getMessage());
        return "errorPage";
    }
}
