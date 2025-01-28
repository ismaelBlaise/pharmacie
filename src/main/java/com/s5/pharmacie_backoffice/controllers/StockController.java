package com.s5.pharmacie_backoffice.controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.s5.pharmacie_backoffice.models.PrixMedicament;
import com.s5.pharmacie_backoffice.repositories.PrixMedicamentRepository;
import com.s5.pharmacie_backoffice.repositories.StockRepository;
import com.s5.pharmacie_backoffice.services.MedicamentFicheService;

@RequestMapping("/stocks")
@Controller
public class StockController {
    @Autowired
    private MedicamentFicheService medicamentFicheService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PrixMedicamentRepository prixMedicamentRepository;
    
    @GetMapping("/suivi-form")
    public ModelAndView suiviForm() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","stock/suivi-form");
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        return modelAndView;
    }

    @GetMapping("/historique-prix-form")
    public ModelAndView historiquePrix() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","stock/historique-prix-form");
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        return modelAndView;
    }

    @GetMapping("/ajouter-prix-form")
    public ModelAndView ajouterPrix() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","prix-medicament/ajout");
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        return modelAndView;
    }


    @PostMapping("/ajouter-prix")
    public ModelAndView ajouterPrix(
            @RequestParam Date date,
            @RequestParam BigDecimal montant,
            @RequestParam Long idMedicamentFiche
    ) {
        try {
            
            ModelAndView modelAndView=new ModelAndView("template");
            PrixMedicament prixMedicament=new PrixMedicament();
            prixMedicament.setDateChangement(date);
            prixMedicament.setMedicamentFiche(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            prixMedicament.setMontant(montant);
            prixMedicamentRepository.save(prixMedicament);
            modelAndView.addObject("success", "prix ajouter avec success");
            
            return modelAndView;
        } catch (Exception e) {
            
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","prix-medicament/ajout");
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/historique")
    public ModelAndView getCommandesAvecCommission(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Long idMedicamentFiche
    ) {
        try {
            
            ModelAndView modelAndView=new ModelAndView("template");
            
            modelAndView.addObject("page","stock/historique-prix");
            modelAndView.addObject("stocks",prixMedicamentRepository.findByDateRangeAndMedicament(startDate, endDate, idMedicamentFiche));
            modelAndView.addObject("fiche",medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            // modelAndView.addObject("stocks", stockRepository.findByDateRangeAndMedicament(startDate, endDate, idMedicamentFiche));
            
            return modelAndView;
        } catch (Exception e) {
            
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","stock/historique-prix-form");
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/suivre")
    public ModelAndView suivre(@RequestParam Long idMedicamentFiche){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("",null);
        return modelAndView;
    }





}
