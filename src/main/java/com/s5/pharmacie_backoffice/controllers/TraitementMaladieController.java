package com.s5.pharmacie_backoffice.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.s5.pharmacie_backoffice.models.TraitementMaladie;
import com.s5.pharmacie_backoffice.models.TraitementMaladieId;
import com.s5.pharmacie_backoffice.services.MaladieService;
import com.s5.pharmacie_backoffice.services.MedicamentFicheService;
import com.s5.pharmacie_backoffice.services.TraitementMaladieService;

@Controller
@RequestMapping("/traitements")
public class TraitementMaladieController {
    @Autowired
    private MaladieService maladieService;

    @Autowired
    private MedicamentFicheService medicamentFicheService;

    @Autowired
    private TraitementMaladieService traitementMaladieService;

    @PostMapping("/recuperer")
    public ModelAndView recupererTraitementMaladies(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long idMaladie
    ) {
        Page<TraitementMaladie> traitementMaladies = traitementMaladieService.recupererTraitementMaladies(page, size, maladieService.recupererMaladie(idMaladie));

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "traitement/liste");
        modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
        modelAndView.addObject("traitementMaladies", traitementMaladies.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", traitementMaladies.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/recuperer")
    public ModelAndView recupererTraitementMaladies2(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long idMaladie
    ) {
        Page<TraitementMaladie> traitementMaladies = traitementMaladieService.recupererTraitementMaladies(page, size, maladieService.recupererMaladie(idMaladie));

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "traitement/liste");
        modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
        modelAndView.addObject("traitementMaladies", traitementMaladies.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", traitementMaladies.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form/{idMaladie}")
    public ModelAndView ajoutForm(@PathVariable Long idMaladie) {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("fiches",medicamentFicheService.recupererMedicamentFiches());
        modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
        modelAndView.addObject("page", "traitement/ajout");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterTraitementMaladie(
            @RequestParam Long idMaladie,
            @RequestParam Long idMedicamentFiche,
            @RequestParam BigDecimal dose,
            @RequestParam Integer frequence,
            @RequestParam String moment,
            @RequestParam BigDecimal duree,
            @RequestParam String instruction 
            ) {

        try {
            TraitementMaladieId traitementMaladieId=new TraitementMaladieId();
            traitementMaladieId.setIdMaladie(idMaladie);
            traitementMaladieId.setIdMedicamentFiche(idMedicamentFiche);

            
            TraitementMaladie traitementMaladie = new TraitementMaladie();
            traitementMaladie.setId(traitementMaladieId);
            traitementMaladie.setMaladie(maladieService.recupererMaladie(idMaladie));
            traitementMaladie.setMedicamentFiche(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            traitementMaladie.setDose(dose);
            traitementMaladie.setFrequence(frequence);
            traitementMaladie.setMoment(moment);
            traitementMaladie.setDuree(duree);
            traitementMaladie.setInstruction(instruction);

            traitementMaladieService.sauvegarderTraitementMaladie(traitementMaladie);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "traitement/ajout");
            modelAndView.addObject("fiches",medicamentFicheService.recupererMedicamentFiches());
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("success", "Traitement maladie ajoutée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("fiches",medicamentFicheService.recupererMedicamentFiches());
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("page", "traitement/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierTraitementMaladie(@RequestParam Long idMaladie,
            @RequestParam Long idMedicamentFiche,
            @RequestParam BigDecimal dose,
            @RequestParam Integer frequence,
            @RequestParam String moment,
            @RequestParam BigDecimal duree,
            @RequestParam String instruction ) 
        {

            TraitementMaladieId traitementMaladieId=new TraitementMaladieId();
            traitementMaladieId.setIdMaladie(idMaladie);
            traitementMaladieId.setIdMedicamentFiche(idMedicamentFiche);

        try {
            
            TraitementMaladie traitementMaladie = new TraitementMaladie();
            traitementMaladie.setId(traitementMaladieId);
            traitementMaladie.setMaladie(maladieService.recupererMaladie(idMaladie));
            traitementMaladie.setMedicamentFiche(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            traitementMaladie.setDose(dose);
            traitementMaladie.setFrequence(frequence);
            traitementMaladie.setMoment(moment);
            traitementMaladie.setDuree(duree);
            traitementMaladie.setInstruction(instruction);

            traitementMaladieService.sauvegarderTraitementMaladie(traitementMaladie);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "traitement/modif");
            modelAndView.addObject("fiches",medicamentFicheService.recupererMedicamentFiches());
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("traitementMaladie", traitementMaladieService.recupererTraitementMaladie(traitementMaladieId));
            modelAndView.addObject("success", "traitement maladie modifiée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("fiches",medicamentFicheService.recupererMedicamentFiches());
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("traitementMaladie", traitementMaladieService.recupererTraitementMaladie(traitementMaladieId));
            modelAndView.addObject("page", "traitement/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{idMedicamentFiche}/{idMaladie}")
    public ModelAndView supprimerTraitementMaladie(@PathVariable Long idMedicamentFiche,@PathVariable Long idMaladie,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            TraitementMaladieId traitementMaladieId=new TraitementMaladieId();
            traitementMaladieId.setIdMaladie(idMaladie);
            traitementMaladieId.setIdMedicamentFiche(idMedicamentFiche);

            traitementMaladieService.supprimerTraitementMaladie(traitementMaladieId);
            Page<TraitementMaladie> traitementMaladies = traitementMaladieService.recupererTraitementMaladies(page, size,maladieService.recupererMaladie(idMaladie));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "traitement/liste");
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("traitementMaladies", traitementMaladies.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", traitementMaladies.getTotalPages());
            return modelAndView;
        } catch (Exception e) {
            Page<TraitementMaladie> traitementMaladies = traitementMaladieService.recupererTraitementMaladies(page, size,maladieService.recupererMaladie(idMaladie));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "traitement/liste");
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("traitementMaladies", traitementMaladies.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", traitementMaladies.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{idMedicamentFiche}/{idMaladie}")
    public ModelAndView modifierFormulaire(@PathVariable Long idMedicamentFiche,@PathVariable Long idMaladie,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            TraitementMaladieId traitementMaladieId=new TraitementMaladieId();
            traitementMaladieId.setIdMaladie(idMaladie);
            traitementMaladieId.setIdMedicamentFiche(idMedicamentFiche);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("fiches",medicamentFicheService.recupererMedicamentFiches());
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("traitementMaladie", traitementMaladieService.recupererTraitementMaladie(traitementMaladieId));
            modelAndView.addObject("page", "traitement/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<TraitementMaladie> traitementMaladies = traitementMaladieService.recupererTraitementMaladies(page, size,maladieService.recupererMaladie(idMaladie));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "traitement/liste");
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("traitementMaladies", traitementMaladies.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", traitementMaladies.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
