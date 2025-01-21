package com.s5.pharmacie_backoffice.controllers;

import java.util.List;

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
import com.s5.pharmacie_backoffice.models.Medicament;
import com.s5.pharmacie_backoffice.models.TraitementMaladie;
import com.s5.pharmacie_backoffice.services.MaladieService;
import com.s5.pharmacie_backoffice.services.MedicamentService;
import com.s5.pharmacie_backoffice.services.TraitementMaladieService;

@Controller
@RequestMapping("/medicaments")
public class MedicamentController {
    @Autowired
    private MedicamentService medicamentService;

    @Autowired
    private MaladieService maladieService;

    @Autowired
    private TraitementMaladieService traitementMaladieService;

    @GetMapping
    public ModelAndView recupererMedicaments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Medicament> medicaments = medicamentService.recupererMedicaments(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "medicament/liste");
        modelAndView.addObject("medicaments", medicaments.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", medicaments.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "medicament/ajout");
        return modelAndView;
    }

    @GetMapping("/recherche-form")
    public ModelAndView rechercheForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "medicament/recherche");
        modelAndView.addObject("maladies", maladieService.recupererMaladies());
        return modelAndView;
    }
    
    @PostMapping("/rechercher")
    public ModelAndView  rechercher(@RequestParam Long idMaladie,@RequestParam String age ){
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page", "medicament/recherche-liste");
        int ageMin=Integer.parseInt(age.split("-")[0]);
        int ageMax=Integer.parseInt(age.split("-")[1]);
        Maladie maladie=maladieService.recupererMaladie(idMaladie);
        List<TraitementMaladie> traitementMaladies=traitementMaladieService.rechercheMedicament(maladie, ageMin, ageMax);
        modelAndView.addObject("traitementMaladies", traitementMaladies);

        return modelAndView;
        

    }

    @PostMapping
    public ModelAndView ajouterMedicament(
            @RequestParam String nomCommercial,@RequestParam String nomScientifique) {

        try {
            Medicament medicament = new Medicament();
            medicament.setNomCommercial(nomCommercial);
            medicament.setNomScientifique(nomScientifique);

            medicamentService.sauvegarderMedicament(medicament);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament/ajout");
            modelAndView.addObject("success", "medicament ajouter avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/modifier")
    public ModelAndView modifierMedicament(@RequestParam Long idMedicament,@RequestParam String nomCommercial,@RequestParam String nomScientifique) {

            try {
                Medicament medicament=new Medicament();
                medicament.setIdMedicament(idMedicament);
                medicament.setNomCommercial(nomCommercial);
                medicament.setNomScientifique(nomScientifique);

                medicamentService.sauvegarderMedicament(medicament);
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "medicament/modif");
                modelAndView.addObject("medicament", medicamentService.recupererMedicament(idMedicament));
                modelAndView.addObject("success", "medicament modifier avec succes");
                return modelAndView;
    
            } catch (Exception e) {
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "medicament/modif");
                modelAndView.addObject("error", e.getMessage());
                return modelAndView;
            }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerMedicament(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            medicamentService.supprimerMedicament(id);
            return new ModelAndView("redirect:/medicaments");
        } catch (Exception e) {
            Page<Medicament> medicaments = medicamentService.recupererMedicaments(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament/liste");
            modelAndView.addObject("medicaments", medicaments.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", medicaments.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("medicament", medicamentService.recupererMedicament(id));
            modelAndView.addObject("page", "medicament/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Medicament> medicaments = medicamentService.recupererMedicaments(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament/liste");
            modelAndView.addObject("medicaments", medicaments.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", medicaments.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
