package com.s5.pharmacie_backoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.s5.pharmacie_backoffice.models.Symptome;
import com.s5.pharmacie_backoffice.services.SymptomeService;

@Controller
@RequestMapping("/symptomes")
public class SymptomeController {
    @Autowired 
    private SymptomeService symptomeService;

    @GetMapping
    public ModelAndView recupererSymptomes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Symptome> symptomes = symptomeService.recupererSymptomes(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "symptome/liste");
        modelAndView.addObject("symptomes", symptomes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", symptomes.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "symptome/ajout");
        return modelAndView;
    }
    

    @PostMapping
    public ModelAndView ajouterSymptome(
            @RequestParam String symptome) {

        try {
            Symptome symptomeObj = new Symptome();
            symptomeObj.setSymptome(symptome);
            symptomeService.sauvegarderSymptome(symptomeObj);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "symptome/ajout");
            modelAndView.addObject("success", "symptome ajouter avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "symptome/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/modifier")
    public ModelAndView modifierSymptome(@RequestParam Long idSymptome,@RequestParam String symptome) {

            try {
                Symptome symptomeObj = new Symptome();
                symptomeObj.setIdSymptome(idSymptome);
                symptomeObj.setSymptome(symptome);
                symptomeService.sauvegarderSymptome(symptomeObj);
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "symptome/modif");
                modelAndView.addObject("symptome", symptomeService.recupererSymptome(idSymptome));
                modelAndView.addObject("success", "symptome modifier avec succes");
                return modelAndView;
    
            } catch (Exception e) {
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "symptome/modif");
                modelAndView.addObject("error", e.getMessage());
                return modelAndView;
            }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerSymptome(@PathVariable Long id
    , @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            symptomeService.supprimerSymptome(id);
            return new ModelAndView("redirect:/symptomes");
        } catch (Exception e) {
            Page<Symptome> symptomes = symptomeService.recupererSymptomes(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "symptome/liste");
            modelAndView.addObject("symptomes", symptomes.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", symptomes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("symptome", symptomeService.recupererSymptome(id));
            modelAndView.addObject("page", "symptome/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Symptome> symptomes = symptomeService.recupererSymptomes(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "symptome/liste");
            modelAndView.addObject("symptomes", symptomes.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", symptomes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
