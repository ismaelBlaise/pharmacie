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
import com.s5.pharmacie_backoffice.models.Forme;
import com.s5.pharmacie_backoffice.services.FormeService;
import com.s5.pharmacie_backoffice.services.VoieAdministrationService;

@Controller
@RequestMapping("/formes")
public class FormeController {
    @Autowired 
    private FormeService formeService;

    @Autowired
    private VoieAdministrationService voieAdministrationService;

    @GetMapping
    public ModelAndView recupererFormes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Forme> formes = formeService.recupererFormes(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "forme/liste");
        modelAndView.addObject("formes", formes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", formes.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "forme/ajout");
        modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
        return modelAndView;
    }
    

    @PostMapping
    public ModelAndView ajouterForme(
            @RequestParam String forme,@RequestParam Long idVoieAdministration) {

        try {
            Forme formeObj = new Forme();
            formeObj.setForme(forme);
            formeObj.setVoieAdministration(voieAdministrationService.recupererVoieAdministration(idVoieAdministration));
            formeService.sauvegarderForme(formeObj);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("page", "forme/ajout");
            modelAndView.addObject("success", "forme ajouter avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "forme/ajout");
            modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/modifier")
    public ModelAndView modifierForme(@RequestParam Long idForme,@RequestParam String forme,@RequestParam Long idVoieAdministration) {

            try {
                Forme formeObj = new Forme();
                formeObj.setIdForme(idForme);
                formeObj.setForme(forme);
                formeObj.setVoieAdministration(voieAdministrationService.recupererVoieAdministration(idVoieAdministration));
                formeService.sauvegarderForme(formeObj);
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "forme/modif");
                modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
                modelAndView.addObject("forme", formeService.recupererForme(idForme));
                modelAndView.addObject("success", "forme modifier avec succes");
                return modelAndView;
    
            } catch (Exception e) {
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
                modelAndView.addObject("page", "forme/modif");
                modelAndView.addObject("error", e.getMessage());
                return modelAndView;
            }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerForme(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            formeService.supprimerForme(id);
            return new ModelAndView("redirect:/formes");
        } catch (Exception e) {
            Page<Forme> formes = formeService.recupererFormes(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "forme/liste");
        modelAndView.addObject("formes", formes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", formes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("forme", formeService.recupererForme(id));
            modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("page", "forme/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Forme> formes = formeService.recupererFormes(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "forme/liste");
        modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
        modelAndView.addObject("formes", formes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", formes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
