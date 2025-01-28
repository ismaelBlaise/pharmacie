package com.s5.pharmacie_backoffice.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.Commande;
import com.s5.pharmacie_backoffice.models.Config;
import com.s5.pharmacie_backoffice.models.DetailPanier;
import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.models.MouvementStock;
import com.s5.pharmacie_backoffice.models.Panier;
import com.s5.pharmacie_backoffice.models.StatutCommande;
import com.s5.pharmacie_backoffice.repositories.CommandeRepository;
import com.s5.pharmacie_backoffice.repositories.ConfigRepository;
import com.s5.pharmacie_backoffice.repositories.DetailPanierRepository;
import com.s5.pharmacie_backoffice.repositories.PanierRepository;
import com.s5.pharmacie_backoffice.repositories.StatutCommandeRepository;
import com.s5.pharmacie_backoffice.repositories.UtilisateurRepository;

@Service
public class VenteService {
     @Autowired
    private MouvementStockService mouvementStockService;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private MedicamentFicheService medicamentFicheService;

     

    @Autowired 
    private ConfigRepository configRepository;

     

    @Autowired
    private UtilisateurRepository uR;

     @Autowired
    private StatutCommandeRepository statutR;

    @Autowired
    private DetailPanierRepository detailPanierRepository;

    @Autowired
    private PanierRepository panierRepository;
    
    public VenteResult traiterVente(Long idMedicamentFiche, Long idUtilisateur, Long idUtilisateurVendeur, Integer quantite, Date dateVente) throws Exception {
        
        MedicamentFiche medicamentFiche = medicamentFicheService.recupererMedicamentFiche(idMedicamentFiche);
        List<MouvementStock> mouvementStocks = mouvementStockService.venteMedicament(medicamentFiche, quantite, dateVente);

        
        Panier panier = obtenirOuCreerPanier(idUtilisateur, dateVente);

         
        double prixTotal = mouvementStockService.prixTotal(mouvementStocks);

         
        ajouterDetailAuPanier(panier, medicamentFiche, quantite);

         
        Commande commande = creerCommande(idUtilisateur, idUtilisateurVendeur, dateVente, prixTotal, panier);

        
        appliquerCommission(commande);

         
        commandeRepository.save(commande);

         
        return new VenteResult(mouvementStocks, panier, commande);
    }

    private Panier obtenirOuCreerPanier(Long idUtilisateur, Date dateVente) {
        List<Panier> paniers = panierRepository.findByUtilisateur(uR.findById(idUtilisateur).orElseThrow(() -> new RuntimeException("Utilisateur introuvable")));
        Panier panier = null;

        for (Panier panierExistant : paniers) {
            if (panierExistant.getEtat() && panierExistant.getDatePanier().isBefore(dateVente.toLocalDate())) {
                panier = panierExistant;
                panier.setEtat(false);
                break;
            }
        }

        if (panier == null) {
            panier = new Panier();
            panier.setUtilisateur(uR.findById(idUtilisateur).orElseThrow(() -> new RuntimeException("Utilisateur introuvable")));
            panier.setEtat(true);
            panier.setDatePanier(dateVente.toLocalDate());
            panierRepository.save(panier);
        }

        return panier;
    }

    private void ajouterDetailAuPanier(Panier panier, MedicamentFiche medicamentFiche, Integer quantite) {
        DetailPanier detailPanier = new DetailPanier();
        detailPanier.setQuantite(quantite);
        detailPanier.setPanier(panier);
        detailPanier.setMedicamentFiche(medicamentFiche);
        detailPanierRepository.save(detailPanier);
    }

    private Commande creerCommande(Long idUtilisateur, Long idUtilisateurVendeur, Date dateVente, double prixTotal, Panier panier) {
        StatutCommande statutCommande = statutR.findByStatut("valider").orElseThrow(() -> new RuntimeException("Statut 'valider' introuvable"));

        Commande commande = new Commande();
        commande.setDateCommande(dateVente.toLocalDate());
        commande.setUtilisateur(uR.findById(idUtilisateur).orElseThrow(() -> new RuntimeException("Utilisateur introuvable")));
        commande.setUtilisateurVendeur(uR.findById(idUtilisateurVendeur).orElseThrow(() -> new RuntimeException("Vendeur introuvable")));
        commande.setPrixTotal(prixTotal);
        commande.setStatutCommande(statutCommande);
        commande.setPanier(panier);

        return commande;
    }

    private void appliquerCommission(Commande commande) throws Exception {
        Config config = configRepository.findByCle("commission").orElseThrow(() -> new Exception("Config 'commission' introuvable"));
        Config configMax = configRepository.findByCle("commission-vente").orElseThrow(() -> new Exception("Config 'commission-vente' introuvable"));

        double commission = Double.parseDouble(config.getVal());
        double commissionVente = Double.parseDouble(configMax.getVal());

        if (commande.getPrixTotal() > commissionVente) {
            commande.setCommission(commande.getPrixTotal() * commission);
        } else {
            commande.setCommission(0);
        }
    }

    public static class VenteResult {
        private final List<MouvementStock> mouvementStocks;
        private final Panier panier;
        private final Commande commande;

        public VenteResult(List<MouvementStock> mouvementStocks, Panier panier, Commande commande) {
            this.mouvementStocks = mouvementStocks;
            this.panier = panier;
            this.commande = commande;
        }

        public List<MouvementStock> getMouvementStocks() {
            return mouvementStocks;
        }

        public Panier getPanier() {
            return panier;
        }

        public Commande getCommande() {
            return commande;
        }
    }

}
