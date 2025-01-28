package com.s5.pharmacie_backoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.s5.pharmacie_backoffice.services.MedicamentFicheService;

@RequestMapping("/stocks")
@Controller
public class StockController {
    @Autowired
    private MedicamentFicheService medicamentFicheService;
    
    @GetMapping("/suivi-form")
    public ModelAndView recherche() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","stock/suivi-form");
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        return modelAndView;
    }


    


}
