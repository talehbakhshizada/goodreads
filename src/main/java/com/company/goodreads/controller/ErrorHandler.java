package com.company.goodreads.controller;

import com.company.goodreads.model.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // nese xeta olsa usere melumat bildirmemisden qabaq gel bu classdan kec sonra bildir.
@Slf4j // classa log parametrini elave edir.loglama ucun istifade olunur.
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handlerNotfoundException(NotFoundException ex){
        log.error("Not found error occurred ", ex);
        ModelAndView mv = new ModelAndView();

        mv.setViewName("error-page");
        mv.addObject("error", ex.getMessage());

        return mv;
    }
}
