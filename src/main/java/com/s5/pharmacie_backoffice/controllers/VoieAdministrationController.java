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

import com.s5.pharmacie_backoffice.models.VoieAdministration;
import com.s5.pharmacie_backoffice.services.VoieAdministrationService;

@Controller
@RequestMapping("/voies-administration")
public class VoieAdministrationController {
    @Autowired
    private VoieAdministrationService voieAdministrationService;

    @GetMapping
    public ModelAndView recupererVoiesAdministration(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<VoieAdministration> voies = voieAdministrationService.recupererVoiesAdministration(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "voie/liste");
        modelAndView.addObject("voies", voies.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", voies.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "voie/ajout");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterVoieAdministration(@RequestParam String voieAdministration) {
        try {
            VoieAdministration voieObj = new VoieAdministration();
            voieObj.setVoieAdministration(voieAdministration);
            voieAdministrationService.sauvegarderVoieAdministration(voieObj);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "voie/ajout");
            modelAndView.addObject("success", "Voie d'administration ajoutée avec succès");
            
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "voie/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierVoieAdministration(
        @RequestParam Long idVoieAdministration,
        @RequestParam String voieAdministration
    ) {
        try {
            VoieAdministration voieObj = new VoieAdministration();
            voieObj.setIdVoieAdministration(idVoieAdministration);
            voieObj.setVoieAdministration(voieAdministration);
            voieAdministrationService.sauvegarderVoieAdministration(voieObj);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "voie/modif");
            modelAndView.addObject("voie", voieAdministrationService.recupererVoieAdministration(idVoieAdministration));
            modelAndView.addObject("success", "Voie d'administration modifiée avec succès");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "voie/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerVoieAdministration(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        try {
            voieAdministrationService.supprimerVoieAdministration(id);
            return new ModelAndView("redirect:/voies-administration");
        } catch (Exception e) {
            Page<VoieAdministration> voies = voieAdministrationService.recupererVoiesAdministration(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "voie/liste");
            modelAndView.addObject("voies", voies.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", voies.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("voie", voieAdministrationService.recupererVoieAdministration(id));
            modelAndView.addObject("page", "voie/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<VoieAdministration> voies = voieAdministrationService.recupererVoiesAdministration(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "voie/liste");
            modelAndView.addObject("voies", voies.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", voies.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
