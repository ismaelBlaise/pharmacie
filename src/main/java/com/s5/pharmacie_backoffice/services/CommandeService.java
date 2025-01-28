package com.s5.pharmacie_backoffice.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.*;
import com.s5.pharmacie_backoffice.repositories.*;

import java.time.LocalDate;
import java.util.*;

@SuppressWarnings("unused")
@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private StatutCommandeRepository statutR;

    
    @Autowired 
    private ConfigRepository configRepository;
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
    
    public List<Commande> commission(LocalDate startDate, LocalDate endDate, Long vendeurId) throws Exception {
        // if(startDate==null && endDate!=null){
        //     return commandeRepository.findByEndDate(endDate);
        // }
        // else if(endDate==null && startDate!=null){
        //     return commandeRepository.findByStartDate(startDate);
        // }
        // else if(startDate==null && endDate==null){
        //     return commandeRepository.findAll();
        // }

        List<Commande> commandes = null;
        if(vendeurId == 0 ){
            commandes = commandeRepository.findByDateRange(startDate, endDate);
        }
        else{
            commandes = commandeRepository.findByDateRangeAndVendeur(startDate, endDate, vendeurId);
        }
        // Optional<StatutCommande> statutCommande=statutR.findByStatut("valider");
        // for(Commande commande:commandes){

        //     if(commande.getStatutCommande().getId()==statutCommande.get().getId()){
        //         Config config=configRepository.findByCle("commission").get();
                
        //         Config configMax=configRepository.findByCle("commission-vente").get();
        //         if(configMax==null || config==null){
        //             throw new Exception("Les configs de commission n'existe pas");
        //         }
        //         double commission=Double.parseDouble(config.getVal());
        //         double commissionVente=Double.parseDouble(configMax.getVal());

        //         if(commande.getPrixTotal().doubleValue()>commissionVente){
        //             commande.setCommission(commande.getPrixTotal().doubleValue()*commission);
        //         }
        //         else{
        //             commande.setCommission(0);
        //         }
        //     }
            
        // }
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

    public List<Commande> filtrerParGenre(List<Commande> commandes, Long idSexe){
        List<Commande> commandesRet = new ArrayList<>();
        if(idSexe == 0){
            return commandes;
        }
        for(Commande commande : commandes){
            if(commande.getUtilisateurVendeur().getSexe().getIdSexe()== idSexe ){
                commandesRet.add(commande);
            }
        }
        return commandesRet;
    }
}
