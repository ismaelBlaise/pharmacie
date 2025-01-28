package com.s5.pharmacie_backoffice.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.s5.pharmacie_backoffice.models.Commande;
import com.s5.pharmacie_backoffice.repositories.ConfigRepository;
import com.s5.pharmacie_backoffice.repositories.SexeRepository;
import com.s5.pharmacie_backoffice.repositories.UtilisateurRepository;
import com.s5.pharmacie_backoffice.services.CommandeService;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("unused")
@Controller
@RequestMapping("/commandes")
public class CommandeController {

   
    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired 
    private SexeRepository sexeRepository;
    
    @GetMapping("/recherche-form")
    public ModelAndView rechercheForm(){
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","commande/recherche");
        return modelAndView;
    }
   
    @PostMapping("/rechercher")
    public ModelAndView rechercher(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "commande/resultat");
        modelAndView.addObject("commandes",commandeService.getClientsByDate(date));
        return modelAndView;
    }

    @GetMapping("/commission-form")
    public ModelAndView commmissionForm(){
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("sexes", sexeRepository.findAll());
        modelAndView.addObject("page","commande/commission-form");
        modelAndView.addObject("utilisateurs",utilisateurRepository.findUtilisateursByRoleName("Vendeur"));
        return modelAndView;
    }

    @PostMapping("/commission")
    public ModelAndView getCommandesAvecCommission(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Long vendeurId, @RequestParam Long idSexe 
    ) {
        try {
            List<Commande> commandes=commandeService.commission(startDate, endDate, vendeurId);
            commandes=commandeService.filtrerParGenre(commandes, idSexe);
            ModelAndView modelAndView=new ModelAndView("template");
            if(idSexe== 0 ){
                modelAndView.addObject("genre", "tous");   
            }
            else{ 
                modelAndView.addObject("genre", sexeRepository.findById(idSexe).get().getSexe());
            }
            modelAndView.addObject("page","commande/commission-resultat");
            modelAndView.addObject("commandes", commandes);
            // modelAndView.addObject("", modelAndView)
            modelAndView.addObject("sommeVente",commandeService.sommeVente(commandes));
            modelAndView.addObject("sommeCommission",commandeService.sommeCommission(commandes));

            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("sexes", sexeRepository.findAll());
            modelAndView.addObject("page","commande/commission-form");
            modelAndView.addObject("utilisateurs",utilisateurRepository.findUtilisateursByRoleName("Vendeur"));
            // modelAndView.addObject("commandes", commandeService.commission(startDate, endDate, vendeurId));
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
