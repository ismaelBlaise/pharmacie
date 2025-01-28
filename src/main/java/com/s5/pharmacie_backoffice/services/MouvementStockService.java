package com.s5.pharmacie_backoffice.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.models.MouvementStock;
import com.s5.pharmacie_backoffice.models.Laboratoire;
import com.s5.pharmacie_backoffice.models.Stock;
import com.s5.pharmacie_backoffice.models.TypeMouvement;
import com.s5.pharmacie_backoffice.repositories.MouvementStockRepository;
import com.s5.pharmacie_backoffice.repositories.StockRepository;
import com.s5.pharmacie_backoffice.repositories.TypeMouvementRepository;

@Service
public class MouvementStockService {
    
    @Autowired 
    private MouvementStockRepository mouvementStockRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private TypeMouvementRepository typeMouvementRepository;


    public List<MouvementStock> venteMedicament(MedicamentFiche medicamentFiche, Integer quantite, Date dateVente) throws Exception {
        
        if (medicamentFiche == null) {
            throw new IllegalArgumentException("Le médicament est obligatoire.");
        }
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité à vendre doit être supérieure à zéro.");
        }
        if (dateVente == null) {
            throw new IllegalArgumentException("La date de vente est obligatoire.");
        }
    
        
        List<Stock> stocks = stockService.recupererStockByMedicamentFiche(medicamentFiche);
        if (stocks == null || stocks.isEmpty()) {
            throw new Exception("Aucun stock disponible pour le médicament spécifié.");
        }
    
        
        stocks = stockService.fifoStocks(stocks, dateVente);
    
         
        TypeMouvement typeMouvement = typeMouvementRepository.findByTypeMouvement("Vente")
                .orElseThrow(() -> new Exception("Le type de mouvement 'Vente' est introuvable."));
        List<MouvementStock> mouvementStocks=new ArrayList<>();
        int quantiteReste = quantite.intValue();
        for (Stock stock : stocks) {
            int quantiteStock = stockService.quantiteRestant(stock);
    
            System.out.println("reste    :"+quantiteStock);
            if (quantiteStock <= 0) {
                continue;
            }
    
             
            int quantiteUtilisee = Math.min(quantiteReste, quantiteStock);
            quantiteReste -= quantiteUtilisee;
    
            MouvementStock mouvementStock = new MouvementStock();
            mouvementStock.setTypeMouvement(typeMouvement);
            mouvementStock.setQuantite(quantiteUtilisee);
            mouvementStock.setDateMouvement(dateVente);
            mouvementStock.setStock(stock);
            
            mouvementStocks.add(mouvementStock);
      
            if (quantiteReste == 0) {
                break;
            }
        }
    
         
        if (quantiteReste > 0) {
            throw new Exception("Stock insuffisant pour satisfaire la quantité demandée.");
        }
        else{
            for (MouvementStock mouvementStock : mouvementStocks) {
                mouvementStockRepository.save(mouvementStock);
            }
        }
        return mouvementStocks;
    }
    
    public double prixTotal(List<MouvementStock> mouvementStocks){
        double prixTotal=0;
        for (MouvementStock mouvementStock : mouvementStocks) {
            prixTotal+=mouvementStock.getStock().getPrix().doubleValue()*mouvementStock.getQuantite().intValue();
        }
        return prixTotal;
    }

    public void achatMedicament(MedicamentFiche medicamentFiche, String lot, BigDecimal prix, BigDecimal prixAchat, Date dateFabrication, 
                                Date dateExpiration, Laboratoire laboratoire, Integer quantite, Date dateAchat) throws IllegalArgumentException, Exception {
        
        if (medicamentFiche == null) {
            throw new IllegalArgumentException("Le médicament est obligatoire.");
        }
        if (lot == null || lot.isEmpty()) {
            throw new IllegalArgumentException("Le numéro de lot est obligatoire.");
        }
        if (prix == null || prix.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le prix doit être supérieur à zéro.");
        }
        if (dateFabrication == null || dateExpiration == null) {
            throw new IllegalArgumentException("Les dates de fabrication et d'expiration sont obligatoires.");
        }
        if (dateExpiration.before(dateFabrication)) {
            throw new IllegalArgumentException("La date d'expiration ne peut pas être antérieure à la date de fabrication.");
        }
        if (laboratoire == null) {
            throw new IllegalArgumentException("Le laboratoire est obligatoire.");
        }
        if (quantite == null || quantite <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à zéro.");
        }
        if (dateAchat == null) {
            throw new IllegalArgumentException("La date d'achat est obligatoire.");
        }

         
        TypeMouvement typeMouvement = typeMouvementRepository.findByTypeMouvement("Achat")
                .orElseThrow(() -> new Exception("Le type de mouvement 'Achat' est introuvable."));

        
        MouvementStock mouvementStock = new MouvementStock();
        mouvementStock.setDateMouvement(dateAchat);
        mouvementStock.setQuantite(quantite);
        mouvementStock.setTypeMouvement(typeMouvement);

         
        Stock stock = new Stock();
        stock.setLot(lot);
        stock.setLaboratoire(laboratoire);
        stock.setDateFabrication(dateFabrication);
        stock.setDateExpiration(dateExpiration);
        stock.setQuantite(quantite);
        stock.setDateStock(dateAchat);
        stock.setPrix(prix);
        stock.setPrixAchat(prixAchat);
        stock.setMedicamentFiche(medicamentFiche);

         
        stockRepository.save(stock);
        mouvementStock.setStock(stock);
        mouvementStockRepository.save(mouvementStock);
    }


    public List<MouvementStock> filtrerParStocks(List<Stock> stocks){
        List<MouvementStock> mouvementStocks=new ArrayList<>();
        for(Stock stock:stocks){
            mouvementStocks.addAll(mouvementStockRepository.findByStock(stock));
        }
        return mouvementStocks;
    }

    public List<MouvementStock> filtrerParType(List<MouvementStock> mouvementStocks,TypeMouvement typeMouvement){
        List<MouvementStock> mouvementStocks1=new ArrayList<>();
        for (MouvementStock mouvementStock : mouvementStocks) {
            if(mouvementStock.getTypeMouvement().getIdTypeMouvement()==typeMouvement.getIdTypeMouvement()){
                mouvementStocks1.add(mouvementStock);
            }
        }
        return mouvementStocks1;
    }
}
