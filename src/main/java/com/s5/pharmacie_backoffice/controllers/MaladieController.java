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
import com.s5.pharmacie_backoffice.models.Maladie;
import com.s5.pharmacie_backoffice.services.MaladieService;

@Controller
@RequestMapping("/maladies")
public class MaladieController {
    @Autowired
    private MaladieService maladieService;

    @GetMapping
    public ModelAndView recupererMaladies(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Maladie> maladies = maladieService.recupererMaladies(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "maladie/liste");
        modelAndView.addObject("maladies", maladies.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", maladies.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "maladie/ajout");
        return modelAndView;
    }

    @GetMapping("/recuperer")
    public ModelAndView recuperer() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("maladies",maladieService.recupererMaladies());
        modelAndView.addObject("page", "maladie-symptome/recherche");
        return modelAndView;
    }


    @GetMapping("/recuperer2")
    public ModelAndView recuperer2() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("maladies",maladieService.recupererMaladies());
        modelAndView.addObject("page", "traitement/recherche");
        return modelAndView;
    }


    @PostMapping
    public ModelAndView ajouterMaladie(
            @RequestParam String maladie,
            @RequestParam String description) {

        try {
            Maladie maladieObj = new Maladie();
            maladieObj.setMaladie(maladie);
            maladieObj.setDescription(description);

            maladieService.sauvegarderMaladie(maladieObj);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie/ajout");
            modelAndView.addObject("success", "maladie ajoutée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierMaladie(@RequestParam Long idMaladie,
            @RequestParam String maladie,
            @RequestParam String description) {

        try {
            Maladie maladieObj = new Maladie();
            maladieObj.setIdMaladie(idMaladie);
            maladieObj.setMaladie(maladie);
            maladieObj.setDescription(description);

            maladieService.sauvegarderMaladie(maladieObj);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie/modif");
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("success", "maladie modifiée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerMaladie(@PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            maladieService.supprimerMaladie(id);
            return new ModelAndView("redirect:/maladies");
        } catch (Exception e) {
            Page<Maladie> maladies = maladieService.recupererMaladies(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie/liste");
            modelAndView.addObject("maladies", maladies.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", maladies.getTotalPages());
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
            modelAndView.addObject("maladie", maladieService.recupererMaladie(id));
            modelAndView.addObject("page", "maladie/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Maladie> maladies = maladieService.recupererMaladies(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie/liste");
            modelAndView.addObject("maladies", maladies.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", maladies.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
