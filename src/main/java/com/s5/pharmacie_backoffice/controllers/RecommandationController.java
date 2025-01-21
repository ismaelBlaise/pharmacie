package com.s5.pharmacie_backoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Date;
import com.s5.pharmacie_backoffice.services.*;

@Controller
@RequestMapping("/recommandations")
public class RecommandationController {

    @Autowired
    private MedicamentFicheService medicamentFicheService;

    @Autowired
    private RecommandationService re;

    @GetMapping("/ajout-form")
    public ModelAndView ajout() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","recommandation/ajout");
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        return modelAndView;
    } 

    @GetMapping("/recherche-form")
    public ModelAndView recherche() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","recommandation/recherche");
        return modelAndView;
    } 

    @PostMapping("/ajouter")
    public ModelAndView ajouter(@RequestParam Long idMedicamentFiche,@RequestParam String raison,@RequestParam Date dateAjout){
        try{
            re.ajouterRecommandation(idMedicamentFiche,raison,dateAjout);
             ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","recommandation/ajout");
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
             modelAndView.addObject("success", "recommandation ajouter avec success");
            return modelAndView;
        }catch(Exception e){
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","recommandation/ajout");
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
             modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/rechercher")
    public ModelAndView rechercher(@RequestParam Date date) {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","recommandation/resultat");
        modelAndView.addObject("date",date);
        modelAndView.addObject("recommandations",re.rechercherRecommandation(date));
        return modelAndView;
    } 


    @GetMapping("/recuperation_par_annee")
    public ModelAndView getRecommandationsByYear(@RequestParam String annee) {
        
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","recommandation/resultat");
        modelAndView.addObject("dete",annee);
        modelAndView.addObject("recommandations",re.getRecommandationsByYear(annee));
        return modelAndView;
    }

    @GetMapping("/recherche_annee")
    public ModelAndView rechercheForme() {
        
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","recommandation/annee");
        
        return modelAndView;
    }
}
