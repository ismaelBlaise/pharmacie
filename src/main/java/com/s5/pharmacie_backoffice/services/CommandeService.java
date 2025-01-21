package com.s5.pharmacie_backoffice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.*;
import com.s5.pharmacie_backoffice.repositories.*;

import java.time.LocalDate;
import java.util.*;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private StatutCommandeRepository statutR;

    // Méthode pour récupérer les utilisateurs ayant acheté à une date donnée
    public List<Commande> getClientsByDate(LocalDate date) {
        List<Commande> commandes = commandeRepository.findByDateCommande(date);
        Optional<StatutCommande> statutCommande=statutR.findByStatut("valider");
        List<Commande> commandesRet=new ArrayList<>(); 
        for(Commande commande:commandes){
            if(commande.getStatutCommande().getId()==statutCommande.get().getId()){
                commandesRet.add(commande);
            }
        }
        return commandesRet;
    }
    
    public List<Commande> commission(LocalDate startDate, LocalDate endDate, Long vendeurId) {
        List<Commande> commandes = commandeRepository.findByDateRangeAndVendeur(startDate, endDate, vendeurId);
        Optional<StatutCommande> statutCommande=statutR.findByStatut("valider");
        for(Commande commande:commandes){
            if(commande.getStatutCommande().getId()==statutCommande.get().getId()){
                commande.setCommission(commande.getPrixTotal().doubleValue()*0.05);
            }
            
        }
        return commandes;
    }

    public double sommeVente(List<Commande> commandes){
        double somme=0;
        for (Commande commande : commandes) {
            somme+=commande.getPrixTotal().doubleValue();
        }
        return somme;
    }

    public double sommeCommission(List<Commande> commandes){
        double somme=0;
        for (Commande commande : commandes) {
            somme+=commande.getCommission();
        }
        return somme;
    }
}
