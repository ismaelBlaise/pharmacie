package com.s5.pharmacie_backoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.s5.pharmacie_backoffice.models.Sexe;
import com.s5.pharmacie_backoffice.services.SexeService;


@Controller
@RequestMapping("/sexes")
public class SexeController {

    @Autowired 
    private SexeService sexeService;

    @GetMapping
    public ModelAndView recupererSexes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Sexe> sexes = sexeService.recupererSexes(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "sexe/liste");
        modelAndView.addObject("sexes", sexes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", sexes.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "sexe/ajout");
        return modelAndView;
    }
    

    @PostMapping
    public ModelAndView ajouterSexe(
            @RequestParam String sexe) {

        try {
            Sexe sexeObj = new Sexe();
            sexeObj.setSexe(sexe);
            sexeService.sauvegarderSexe(sexeObj);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "sexe/ajout");
            modelAndView.addObject("success", "sexe ajouter avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "sexe/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/modifier")
    public ModelAndView modifierSexe(@RequestParam Long idSexe,@RequestParam String sexe) {

            try {
                Sexe sexeObj = new Sexe();
                sexeObj.setIdSexe(idSexe);
                sexeObj.setSexe(sexe);
                sexeService.sauvegarderSexe(sexeObj);
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "sexe/modif");
                modelAndView.addObject("sexe", sexeService.recupererSexe(idSexe));
                modelAndView.addObject("success", "sexe modifier avec succes");
                return modelAndView;
    
            } catch (Exception e) {
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "sexe/modif");
                modelAndView.addObject("error", e.getMessage());
                return modelAndView;
            }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerSexe(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            sexeService.supprimerSexe(id);
            return new ModelAndView("redirect:/sexes");
        } catch (Exception e) {
            Page<Sexe> sexes = sexeService.recupererSexes(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "sexe/liste");
        modelAndView.addObject("sexes", sexes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", sexes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("sexe", sexeService.recupererSexe(id));
            modelAndView.addObject("page", "sexe/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Sexe> sexes = sexeService.recupererSexes(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "sexe/liste");
            modelAndView.addObject("sexes", sexes.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", sexes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
