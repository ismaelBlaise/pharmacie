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
import com.s5.pharmacie_backoffice.models.Laboratoire;
import com.s5.pharmacie_backoffice.services.LaboratoireService;

@Controller
@RequestMapping("/laboratoires")
public class LaboratoireController {
    @Autowired
    private LaboratoireService laboratoireService;

    @GetMapping
    public ModelAndView recupererLaboratoires(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Laboratoire> laboratoires = laboratoireService.recupererLaboratoires(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "laboratoire/liste");
        modelAndView.addObject("laboratoires", laboratoires.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", laboratoires.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-laboratoire")
    public ModelAndView ajoutLaboratoire() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "laboratoire/ajout");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterLaboratoire(
            @RequestParam String nom
    ) {
        try {
            Laboratoire laboratoire = new Laboratoire();
            laboratoire.setNom(nom);
            laboratoireService.sauvegarderLaboratoire(laboratoire);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "laboratoire/ajout");
            modelAndView.addObject("success", "Laboratoire ajouté avec succès");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "laboratoire/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierLaboratoire(@RequestParam Long idLaboratoire, @RequestParam String nom) {
        try {
            Laboratoire laboratoire = new Laboratoire();
            laboratoire.setIdLaboratoire(idLaboratoire);
            laboratoire.setNom(nom);
            laboratoireService.sauvegarderLaboratoire(laboratoire);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "laboratoire/modif");
            modelAndView.addObject("laboratoire", laboratoireService.recupererLaboratoire(idLaboratoire));
            modelAndView.addObject("success", "Laboratoire modifié avec succès");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "laboratoire/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerLaboratoire(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        try {
            laboratoireService.supprimerLaboratoire(id);
            return new ModelAndView("redirect:/laboratoires");
        } catch (Exception e) {
            Page<Laboratoire> laboratoires = laboratoireService.recupererLaboratoires(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "laboratoire/liste");
            modelAndView.addObject("laboratoires", laboratoires.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", laboratoires.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-laboratoire/{id}")
    public ModelAndView modifierLaboratoire(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("laboratoire", laboratoireService.recupererLaboratoire(id));
            modelAndView.addObject("page", "laboratoire/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Laboratoire> laboratoires = laboratoireService.recupererLaboratoires(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "laboratoire/liste");
            modelAndView.addObject("laboratoires", laboratoires.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", laboratoires.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
