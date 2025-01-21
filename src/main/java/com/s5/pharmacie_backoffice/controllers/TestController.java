package com.s5.pharmacie_backoffice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TestController {

    @GetMapping
    public ModelAndView connexionPage() {
        ModelAndView modelAndView = new ModelAndView("login");
        
        return modelAndView;
    }

    @GetMapping("template")
    public ModelAndView template() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "pages/accueil");
        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView modelAndView = new ModelAndView("error");
        
        return modelAndView;
    }


}
