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

import com.s5.pharmacie_backoffice.models.MedicamentComposition;
import com.s5.pharmacie_backoffice.models.MedicamentCompositionId;
import com.s5.pharmacie_backoffice.services.CompositionService;
import com.s5.pharmacie_backoffice.services.MedicamentCompositionService;
import com.s5.pharmacie_backoffice.services.MedicamentFicheService;

@Controller
@RequestMapping("/medicaments-compositions")
public class MedicamentCompositionController {
    @Autowired
    private CompositionService compositionService;

    @Autowired
    private MedicamentFicheService medicamentFicheService;

    @Autowired
    private MedicamentCompositionService medicamentCompositionService;

    @PostMapping("/recuperer")
    public ModelAndView recupererCompositionMedicaments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long idMedicamentFiche
    ) {
        Page<MedicamentComposition> medicamentCompositions = medicamentCompositionService.recupererCompositionsMedicament(page, size, medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "medicament-composition/liste");
        modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
        modelAndView.addObject("medicamentCompositions", medicamentCompositions.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", medicamentCompositions.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/recuperer")
    public ModelAndView recupererCompositionMedicaments2(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long idMedicamentFiche
    ) {
        Page<MedicamentComposition> medicamentCompositions = medicamentCompositionService.recupererCompositionsMedicament(page, size, medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "medicament-composition/liste");
        modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
        modelAndView.addObject("medicamentCompositions", medicamentCompositions.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", medicamentCompositions.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form/{idMedicamentFiche}")
    public ModelAndView ajoutForm(@PathVariable Long idMedicamentFiche) {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("compositions",compositionService.recupererCompositions());
        modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
        modelAndView.addObject("page", "medicament-composition/ajout");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterCompositionMedicament(
            @RequestParam Long idMedicamentFiche,
            @RequestParam Long idComposition,
            @RequestParam BigDecimal poid) {

        try {
            MedicamentCompositionId medicamentCompositionId = new MedicamentCompositionId();
            medicamentCompositionId.setIdComposition(idComposition);
            medicamentCompositionId.setIdMedicamentFiche(idMedicamentFiche);
            MedicamentComposition medicamentComposition = new MedicamentComposition();
            medicamentComposition.setId(medicamentCompositionId);
            medicamentComposition.setComposition(compositionService.recupererComposition(idComposition));
            medicamentComposition.setMedicamentFiche(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            medicamentComposition.setPoid(poid);

            medicamentCompositionService.sauvegarderCompositionMedicament(medicamentComposition);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament-composition/ajout");
            modelAndView.addObject("compositions",compositionService.recupererCompositions());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("success", "Composition medicament ajoutée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("compositions",compositionService.recupererCompositions());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("page", "medicament-composition/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierCompositionMedicament(@RequestParam Long idComposition,
            @RequestParam Long idMedicamentFiche,
            @RequestParam BigDecimal poid) {

                MedicamentCompositionId medicamentCompositionId = new MedicamentCompositionId();
                medicamentCompositionId.setIdComposition(idComposition);
                medicamentCompositionId.setIdMedicamentFiche(idMedicamentFiche);
        try {
            
            MedicamentComposition medicamentComposition = new MedicamentComposition();
            medicamentComposition.setId(medicamentCompositionId);
            medicamentComposition.setComposition(compositionService.recupererComposition(idComposition));
            medicamentComposition.setMedicamentFiche(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            medicamentComposition.setPoid(poid);

            
            medicamentCompositionService.sauvegarderCompositionMedicament(medicamentComposition);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament-composition/modif");
            modelAndView.addObject("compositions",compositionService.recupererCompositions());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("medicamentComposition", medicamentCompositionService.recupererCompositionMedicament(medicamentCompositionId));
            modelAndView.addObject("success", "Composition medicament modifiée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("compositions",compositionService.recupererCompositions());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("medicamentComposition", medicamentCompositionService.recupererCompositionMedicament(medicamentCompositionId));
            modelAndView.addObject("page", "composition/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{idComposition}/{idMedicamentFiche}")
    public ModelAndView supprimerCompositionMedicament(@PathVariable Long idComposition,@PathVariable Long idMedicamentFiche,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            MedicamentCompositionId medicamentCompositionId = new MedicamentCompositionId();
            medicamentCompositionId.setIdComposition(idComposition);
            medicamentCompositionId.setIdMedicamentFiche(idMedicamentFiche);
            medicamentCompositionService.supprimerCompositionMedicament(medicamentCompositionId); // Assumes the ID is only for MedicamentComposition
            Page<MedicamentComposition> medicamentCompositions = medicamentCompositionService.recupererCompositionsMedicament(page, size, medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament-composition/liste");
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("medicamentCompositions", medicamentCompositions.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", medicamentCompositions.getTotalPages());
            return modelAndView;
        } catch (Exception e) {
            Page<MedicamentComposition> medicamentCompositions = medicamentCompositionService.recupererCompositionsMedicament(page, size, medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament-composition/liste");
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("medicamentCompositions", medicamentCompositions.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", medicamentCompositions.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{idComposition}/{idMedicamentFiche}")
    public ModelAndView modifierFormulaire(@PathVariable Long idComposition,@PathVariable Long idMedicamentFiche,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            MedicamentCompositionId medicamentCompositionId = new MedicamentCompositionId();
            medicamentCompositionId.setIdComposition(idComposition);
            medicamentCompositionId.setIdMedicamentFiche(idMedicamentFiche);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("compositions",compositionService.recupererCompositions());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("medicamentComposition", medicamentCompositionService.recupererCompositionMedicament(medicamentCompositionId));
            modelAndView.addObject("page", "medicament-composition/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<MedicamentComposition> medicamentCompositions = medicamentCompositionService.recupererCompositionsMedicament(page, size, medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament-composition/liste");
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            modelAndView.addObject("medicamentCompositions", medicamentCompositions.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", medicamentCompositions.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
