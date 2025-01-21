package com.s5.pharmacie_backoffice.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.*;
import com.s5.pharmacie_backoffice.repositories.*;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private MouvementStockRepository mouvementStockRepository;
    
    @Autowired
    private TypeMouvementRepository typeMouvementRepository;
    
    public List<Stock> recupererStockByMedicamentFiche(MedicamentFiche medicamentFiche){
        return stockRepository.findByMedicamentFiche(medicamentFiche);
    }

    public List<Stock> filtrerStockParDate(List<Stock> stocks, boolean estDesc) {
        
        List<Stock> stocksRet = new ArrayList<>(stocks);

         
        stocksRet.sort(Comparator.comparing(Stock::getDateStock));

        
        if (estDesc) {
            stocksRet.sort(Comparator.comparing(Stock::getDateStock).reversed());
        }

        return stocksRet;
    }

     
    public List<Stock> fifoStocks(List<Stock> stocks, Date dateVente) {
        List<Stock> stocksRet = new ArrayList<>();

        for (Stock stock : stocks) {
             
            // if (stock.getQuantite() > 0 && (stock.getDateExpiration() == null || stock.getDateExpiration().after(dateVente)) && stock.getDateStock().before(dateVente)) {
            //     stocksRet.add(stock);
            // }
            if (stock.getQuantite() > 0) {
                stocksRet.add(stock);
            }
        }

        
        // stocksRet=filtrerStockParDate(stocksRet, true);

        return stocksRet;
    }

    public Integer quantiteRestant(Stock stock) throws Exception{
        List<MouvementStock> mouvementStocks=mouvementStockRepository.findByStock(stock);
        int quReste=stock.getQuantite();
        TypeMouvement typeMouvement = typeMouvementRepository.findByTypeMouvement("Vente")
                .orElseThrow(() -> new Exception("Le type de mouvement 'Vente' est introuvable."));
        for(MouvementStock mouvementStock:mouvementStocks){
            if(mouvementStock.getTypeMouvement().getIdTypeMouvement()==typeMouvement.getIdTypeMouvement())
                quReste-=mouvementStock.getQuantite();
        }

        return quReste;
    } 

    public List<Stock> filtrerParMedicamentFiche(List<MedicamentFiche> medicamentFiches){
        List<Stock> stocks=new ArrayList<>();

        for(MedicamentFiche medicamentFiche:medicamentFiches){
            stocks.addAll(stockRepository.findByMedicamentFiche(medicamentFiche));
        }
        return stocks;
    }
}
