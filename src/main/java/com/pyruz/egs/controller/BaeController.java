package com.pyruz.egs.controller;

import com.pyruz.egs.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaeController {

    @GetMapping("exceptionPage")
    public ModelAndView exceptionPage(@ModelAttribute("exceptionDetail") ServiceException exceptionDetail) {
        ModelAndView modelAndView = new ModelAndView("exception-page");
        modelAndView.addObject("exceptionDetail", exceptionDetail);
        return modelAndView;
    }

    @GetMapping("exception")
    public ModelAndView exception(@ModelAttribute("exceptionDetail") ServiceException exceptionDetail) {
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("exceptionDetail", exceptionDetail);
        return modelAndView;
    }
}
