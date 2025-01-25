package com.s5.pharmacie_backoffice.controllers;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.s5.pharmacie_backoffice.models.Commande;
import com.s5.pharmacie_backoffice.models.DetailPanier;
import com.s5.pharmacie_backoffice.models.Forme;
import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.models.MouvementStock;
import com.s5.pharmacie_backoffice.models.Panier;
import com.s5.pharmacie_backoffice.models.StatutCommande;
import com.s5.pharmacie_backoffice.models.Stock;
import com.s5.pharmacie_backoffice.models.TypeMouvement;
import com.s5.pharmacie_backoffice.models.Config;
import com.s5.pharmacie_backoffice.repositories.*;
import com.s5.pharmacie_backoffice.services.MedicamentFicheService;
import com.s5.pharmacie_backoffice.services.MouvementStockService;
import com.s5.pharmacie_backoffice.services.StockService;
import com.s5.pharmacie_backoffice.services.VoieAdministrationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/mouvements-stock")
public class MouvementStockController {
    @Autowired
    private MouvementStockService mouvementStockService;

    @Autowired
    private MouvementStockRepository mouvementStockRepository;

    @Autowired
    private MedicamentFicheService medicamentFicheService;

    @Autowired
    private FormeRepository formeRepository;

    @Autowired 
    private ConfigRepository configRepository;

    @Autowired
    private VoieAdministrationService voieAdministrationService;

    @Autowired
    private StockService stockService;

    @Autowired
    private TypeMouvementRepository typeMouvementRepository;

    @Autowired
    private LaboratoireRepository laboratoireRepository;

    @Autowired
    private UtilisateurRepository uR;

     @Autowired
    private StatutCommandeRepository statutR;

    @Autowired
    private DetailPanierRepository detailPanierRepository;

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private CommandeRepository commandeRepository;
    @GetMapping("/achat-form")
    public ModelAndView achat() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","mouvement-stock/achat");
        modelAndView.addObject("laboratoires",laboratoireRepository.findAll());
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Achat").get();
        modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
        return modelAndView;
    } 

    @GetMapping("/vente-form")
    public ModelAndView vente() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","mouvement-stock/vente");
        modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
        TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Vente").get();
        modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
        modelAndView.addObject("utilisateurs",uR.findUtilisateursByRoleName("Utilisateur"));

        modelAndView.addObject("utilisateursV",uR.findUtilisateursByRoleName("Vendeur"));

        return modelAndView;
    } 

    @GetMapping("/recherche-form")
    public ModelAndView recherche() {
        ModelAndView modelAndView=new ModelAndView("template");
        modelAndView.addObject("page","mouvement-stock/recherche");
        modelAndView.addObject("voieAdministrations", voieAdministrationService.recupererVoiesAdministration());
        TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Vente").get();
        modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
        return modelAndView;
    }

    @PostMapping("/ajouter-vente")
    public ModelAndView ajouterVente(@RequestParam Long idMedicamentFiche,@RequestParam  Long idUtilisateur,@RequestParam  Long idUtilisateur2,@RequestParam Integer quantite,@RequestParam Date dateVente ) {
       try {
            List<MouvementStock> mouvementStocks=mouvementStockService.venteMedicament(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche), quantite, dateVente);
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","mouvement-stock/vente");
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
            TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Vente").get();
            modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));

            StatutCommande statutCommande=statutR.findByStatut("valider").get();
            Panier panier=null;
            List<Panier> paniers=panierRepository.findByUtilisateur(uR.findById(idUtilisateur).get());

            for (Panier panier2 : paniers) {
                if (panier2.getEtat()==true && panier2.getDatePanier().isBefore(dateVente.toLocalDate())) {
                    panier=panier2;
                }
            }
            if(panier==null){
                panier=new Panier();
                panier.setUtilisateur(uR.findById(idUtilisateur).get());
                panier.setEtat(true);
                panier.setDatePanier(dateVente.toLocalDate());
                panierRepository.save(panier);
            }else{
                panier.setEtat(false);
            }
            double prixTotal=mouvementStockService.prixTotal(mouvementStocks);
            DetailPanier detailPanier=new DetailPanier();
            detailPanier.setQuantite(quantite);
            detailPanier.setPanier(panier);
            detailPanier.setMedicamentFiche(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche));
            detailPanierRepository.save(detailPanier);
            Commande commande=new Commande();
            commande.setDateCommande(dateVente.toLocalDate());
            commande.setUtilisateur(uR.findById(idUtilisateur).get());
            commande.setUtilisateurVendeur(uR.findById(idUtilisateur2).get());
            commande.setPrixTotal(prixTotal);
            commande.setPanier(null);
            commande.setStatutCommande(statutCommande);
            commande.setPanier(panier);
            
            Config config=configRepository.findByCle("commission").get();
                
                Config configMax=configRepository.findByCle("commission-vente").get();
                if(configMax==null || config==null){
                    throw new Exception("Les configs de commission n'existe pas");
                }
                double commission=Double.parseDouble(config.getVal());
                double commissionVente=Double.parseDouble(configMax.getVal());

                if(commande.getPrixTotal().doubleValue()>commissionVente){
                    commande.setCommission(commande.getPrixTotal().doubleValue()*commission);
                }
                else{
                    commande.setCommission(0);
                }
            commandeRepository.save(commande);
            modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
            modelAndView.addObject("utilisateurs",uR.findUtilisateursByRoleName("Utilisateur"));
    
            modelAndView.addObject("utilisateursV",uR.findUtilisateursByRoleName("Vendeur"));
            modelAndView.addObject("success","Vente ajouter avec succees");
            return modelAndView;
       } catch (Exception e) {
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","mouvement-stock/vente");
            modelAndView.addObject("utilisateurs",uR.findUtilisateursByRoleName("Utilisateur"));
    
            modelAndView.addObject("utilisateursV",uR.findUtilisateursByRoleName("Vendeur"));
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
            TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Vente").get();
            modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
            modelAndView.addObject("error",e.getMessage());
        return modelAndView;
       }
    }


    @PostMapping("/ajouter-achat")
    public ModelAndView ajouterAchat(@RequestParam Long idMedicamentFiche,@RequestParam Long idLaboratoire,@RequestParam String lot,@RequestParam BigDecimal prix,@RequestParam Date dateFabrication,@RequestParam Date dateExpiration,@RequestParam Integer quantite,@RequestParam Date dateAchat ) {
       try {
            mouvementStockService.achatMedicament(medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche), lot, prix, dateFabrication, dateExpiration,laboratoireRepository.findById(idLaboratoire).get(),quantite, dateAchat);
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","mouvement-stock/achat");
            modelAndView.addObject("laboratoires",laboratoireRepository.findAll());
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
            TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Achat").get();
            modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
            modelAndView.addObject("success","achat effectuer avec succees");
            return modelAndView;
       } catch (Exception e) {
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","mouvement-stock/achat");
            modelAndView.addObject("laboratoires",laboratoireRepository.findAll());
            modelAndView.addObject("fiches", medicamentFicheService.recupererMedicamentFiches());
            TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Achat").get();
            modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
            modelAndView.addObject("error",e.getMessage());
        return modelAndView;
       }
    }
    

    @PostMapping("/rechercher")
    public ModelAndView rechercher(@RequestParam Long idVoieAdministration,@RequestParam String age ) {
       try {
            int ageMin=Integer.parseInt(age.split("-")[0]);
            int ageMax=Integer.parseInt(age.split("-")[1]);

            List<Forme> formes=formeRepository.findByVoieAdministration(voieAdministrationService.recupererVoieAdministration(idVoieAdministration));

            List<MedicamentFiche> medicamentFiches=medicamentFicheService.filtrerByForme(formes);
            medicamentFiches=medicamentFicheService.filtrerParTrancheAge(medicamentFiches,ageMin,ageMax);

            List<Stock> stocks=stockService.filtrerParMedicamentFiche(medicamentFiches);

            List<MouvementStock> mouvementStocks=mouvementStockService.filtrerParStocks(stocks);
            mouvementStocks=mouvementStockService.filtrerParType(mouvementStocks, typeMouvementRepository.findByTypeMouvement("Vente").get());
           
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","mouvement-stock/recherche-liste");
            modelAndView.addObject("mouvementStocks",mouvementStocks);
            
            return modelAndView;
       } catch (Exception e) {
            ModelAndView modelAndView=new ModelAndView("template");
            modelAndView.addObject("page","mouvement-stock/recherche");
            modelAndView.addObject("voieAdministrations", voieAdministrationService.recupererVoiesAdministration());
            TypeMouvement typeMouvement=typeMouvementRepository.findByTypeMouvement("Vente").get();
            modelAndView.addObject("mouvementStocks",mouvementStockRepository.findByTypeMouvement(typeMouvement));
            modelAndView.addObject("error",e.getMessage());
        return modelAndView;
       }
    }
}