package com.s5.pharmacie_backoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.services.FormeService;
import com.s5.pharmacie_backoffice.services.MedicamentFicheService;
import com.s5.pharmacie_backoffice.services.MedicamentService;
import com.s5.pharmacie_backoffice.services.TypeMedicamentService;
import jakarta.servlet.annotation.MultipartConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@MultipartConfig
@Controller
@RequestMapping("/medicament-fiches")
public class MedicamentFicheController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private MedicamentFicheService medicamentFicheService;

    @Autowired
    private MedicamentService medicamentService;

    @Autowired
    private TypeMedicamentService typeMedicamentService;

    @Autowired
    private FormeService formeService;

    @GetMapping("/recuperer")
    public ModelAndView recupererMedicamentFichesRecherche(){
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","medicament-composition/recherche");
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        return modelAndView;
    }

    @GetMapping
    public ModelAndView recupererMedicamentFiches(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<MedicamentFiche> fiches = medicamentFicheService.recupererMedicamentFiches(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "medicament-fiche/liste");
        modelAndView.addObject("fiches", fiches.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", fiches.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("medicaments",medicamentService.recupererMedicaments());
        modelAndView.addObject("formes", formeService.recupererFormes());
        // modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
        modelAndView.addObject("types", typeMedicamentService.recupererTypeMedicaments());
        modelAndView.addObject("page", "medicament-fiche/ajout");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterMedicamentFiche(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam BigDecimal dosage,
            @RequestParam Integer ageMin,
            @RequestParam Integer ageMax,
            @RequestParam BigDecimal poidMin,
            @RequestParam BigDecimal poidMax,
            @RequestParam String indication,
            @RequestParam Long idForme,
            @RequestParam Long idTypeMedicament,
            @RequestParam Long idMedicament
    ) {
        try {
            // Upload de la photo
            String fileName = saveFile(photo);
    
            MedicamentFiche fiche = new MedicamentFiche();
            fiche.setPhoto(fileName);  // Le nom du fichier ou son chemin
            fiche.setDosage(dosage);
            fiche.setAgeMin(ageMin);
            fiche.setAgeMax(ageMax);
            fiche.setPoidMin(poidMin);
            fiche.setPoidMax(poidMax);
            fiche.setIndication(indication);
            fiche.setMedicament(medicamentService.recupererMedicament(idMedicament));
            fiche.setForme(formeService.recupererForme(idForme));
            fiche.setTypeMedicament(typeMedicamentService.recupererTypeMedicament(idTypeMedicament));

    
            medicamentFicheService.sauvegarderMedicamentFiche(fiche);
            
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("medicaments", medicamentService.recupererMedicaments());
            modelAndView.addObject("formes", formeService.recupererFormes());
            // modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("types", typeMedicamentService.recupererTypeMedicaments());
            modelAndView.addObject("page", "medicament-fiche/ajout");
            modelAndView.addObject("success", "Fiche de médicament ajoutée avec succès");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("medicaments", medicamentService.recupererMedicaments());
            modelAndView.addObject("formes", formeService.recupererFormes());
            // modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("types", typeMedicamentService.recupererTypeMedicaments());
            modelAndView.addObject("page", "medicament-fiche/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
    

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(id));
            
        modelAndView.addObject("medicaments",medicamentService.recupererMedicaments());
            modelAndView.addObject("formes", formeService.recupererFormes());
            // modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("types", typeMedicamentService.recupererTypeMedicaments());
            modelAndView.addObject("page", "medicament-fiche/modif");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("medicaments",medicamentService.recupererMedicaments());
            modelAndView.addObject("formes", formeService.recupererFormes());
            // modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(id));
            modelAndView.addObject("types", typeMedicamentService.recupererTypeMedicaments());
            modelAndView.addObject("page", "medicament-fiche/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierMedicamentFiche(
            @RequestParam Long idFiche,
            @RequestParam BigDecimal dosage,
            @RequestParam Integer ageMin,
            @RequestParam Integer ageMax,
            @RequestParam BigDecimal poidMin,
            @RequestParam BigDecimal poidMax,
            @RequestParam String indication,
            @RequestParam Long idForme,
            @RequestParam Long idTypeMedicament,
            @RequestParam Long idMedicament,
            @RequestParam("photo") MultipartFile photo
    ) {
        try {
            MedicamentFiche fiche = medicamentFicheService.recupererMedicamentFiche(idFiche);

            // Upload de la nouvelle photo si fournie
            if (!photo.isEmpty()) {
                String fileName = saveFile(photo);
                fiche.setPhoto(fileName);
            }
            
            fiche.setDosage(dosage);
            fiche.setAgeMin(ageMin);
            fiche.setAgeMax(ageMax);
            fiche.setPoidMin(poidMin);
            fiche.setPoidMax(poidMax);
            fiche.setIndication(indication);
            fiche.setMedicament(medicamentService.recupererMedicament(idMedicament));
            fiche.setForme(formeService.recupererForme(idForme));
            fiche.setTypeMedicament(typeMedicamentService.recupererTypeMedicament(idTypeMedicament));
            

            medicamentFicheService.sauvegarderMedicamentFiche(fiche);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("medicaments",medicamentService.recupererMedicaments());
            modelAndView.addObject("formes", formeService.recupererFormes());
            // modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("types", typeMedicamentService.recupererTypeMedicaments());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idFiche));

            modelAndView.addObject("page", "medicament-fiche/modif");
            modelAndView.addObject("success", "Fiche de médicament modifiée avec succès");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("medicaments",medicamentService.recupererMedicaments());
            modelAndView.addObject("formes", formeService.recupererFormes());
            // modelAndView.addObject("voies", voieAdministrationService.recupererVoiesAdministration());
            modelAndView.addObject("types", typeMedicamentService.recupererTypeMedicaments());
            modelAndView.addObject("fiche", medicamentFicheService.recupererMedicamentFiche(idFiche));

            modelAndView.addObject("page", "medicament-fiche/modif");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerMedicamentFiche(@PathVariable Long id, @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            medicamentFicheService.supprimerMedicamentFiche(id);
            return new ModelAndView("redirect:/medicament-fiches");
        } catch (Exception e) {
            Page<MedicamentFiche> fiches = medicamentFicheService.recupererMedicamentFiches(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "medicament-fiche/liste");
            modelAndView.addObject("fiches", fiches.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", fiches.getTotalPages());
             
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (!Files.exists(Paths.get(UPLOAD_DIR))) {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        }
        String fileName =  UUID.randomUUID().toString() + "_" +file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.write(path, file.getBytes());
        return fileName;
    }
}
