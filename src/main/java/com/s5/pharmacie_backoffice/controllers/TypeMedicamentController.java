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
import com.s5.pharmacie_backoffice.models.TypeMedicament;
import com.s5.pharmacie_backoffice.services.TypeMedicamentService;

@Controller
@RequestMapping("/types-medicament")
public class TypeMedicamentController {
    @Autowired 
    private TypeMedicamentService typeMedicamentService;

    @GetMapping
    public ModelAndView recupererTypeMedicaments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<TypeMedicament> typeMedicaments = typeMedicamentService.recupererTypeMedicaments(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "type/liste");
        modelAndView.addObject("typeMedicaments", typeMedicaments.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", typeMedicaments.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "type/ajout");
        return modelAndView;
    }
    

    @PostMapping
    public ModelAndView ajouterTypeMedicament(
            @RequestParam String typeMedicament) {

        try {
            TypeMedicament typeMedicamentObj = new TypeMedicament();
            typeMedicamentObj.setTypeMedicament(typeMedicament);
            typeMedicamentService.sauvegarderTypeMedicament(typeMedicamentObj);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "type/ajout");
            modelAndView.addObject("success", "typeMedicament ajouter avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "type/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/modifier")
    public ModelAndView modifierTypeMedicament(@RequestParam Long idTypeMedicament,@RequestParam String typeMedicament) {

            try {
                TypeMedicament typeMedicamentObj = new TypeMedicament();
                typeMedicamentObj.setIdTypeMedicament(idTypeMedicament);
                typeMedicamentObj.setTypeMedicament(typeMedicament);
                typeMedicamentService.sauvegarderTypeMedicament(typeMedicamentObj);
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "type/modif");
                modelAndView.addObject("typeMedicament", typeMedicamentService.recupererTypeMedicament(idTypeMedicament));
                modelAndView.addObject("success", "typeMedicament modifier avec succes");
                return modelAndView;
    
            } catch (Exception e) {
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "type/modif");
                modelAndView.addObject("error", e.getMessage());
                return modelAndView;
            }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerTypeMedicament(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            typeMedicamentService.supprimerTypeMedicament(id);
            return new ModelAndView("redirect:/types-medicament");
        } catch (Exception e) {
            Page<TypeMedicament> typeMedicaments = typeMedicamentService.recupererTypeMedicaments(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "type/liste");
        modelAndView.addObject("typeMedicaments", typeMedicaments.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", typeMedicaments.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("typeMedicament", typeMedicamentService.recupererTypeMedicament(id));
            modelAndView.addObject("page", "type/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<TypeMedicament> typeMedicaments = typeMedicamentService.recupererTypeMedicaments(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "type/liste");
            modelAndView.addObject("typeMedicaments", typeMedicaments.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", typeMedicaments.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
