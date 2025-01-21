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
import com.s5.pharmacie_backoffice.models.Composition;
import com.s5.pharmacie_backoffice.services.CompositionService;

@Controller
@RequestMapping("/compositions")
public class CompositionController {
    @Autowired
    private CompositionService compositionService;

    @GetMapping
    public ModelAndView recupererCompositions(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Composition> compositions = compositionService.recupererCompositions(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "composition/liste");
        modelAndView.addObject("compositions", compositions.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", compositions.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "composition/ajout");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterComposition(
            @RequestParam String nom,
            @RequestParam String description) {

        try {
            Composition composition = new Composition();
            composition.setNom(nom);
            composition.setDescription(description);

            compositionService.sauvegarderComposition(composition);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "composition/ajout");
            modelAndView.addObject("success", "Composition ajoutée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "composition/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierComposition(@RequestParam Long idComposition,
            @RequestParam String nom,
            @RequestParam String description) {

        try {
            Composition composition = new Composition();
            composition.setIdComposition(idComposition);
            composition.setNom(nom);
            composition.setDescription(description);

            compositionService.sauvegarderComposition(composition);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "composition/modif");
            modelAndView.addObject("composition", compositionService.recupererComposition(idComposition));
            modelAndView.addObject("success", "Composition modifiée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "composition/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerComposition(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            compositionService.supprimerComposition(id);
            return new ModelAndView("redirect:/compositions");
        } catch (Exception e) {
            Page<Composition> compositions = compositionService.recupererCompositions(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "composition/liste");
            modelAndView.addObject("compositions", compositions.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", compositions.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("composition", compositionService.recupererComposition(id));
            modelAndView.addObject("page", "composition/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Composition> compositions = compositionService.recupererCompositions(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "composition/liste");
            modelAndView.addObject("compositions", compositions.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", compositions.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
