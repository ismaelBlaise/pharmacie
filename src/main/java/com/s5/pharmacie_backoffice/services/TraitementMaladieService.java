package com.s5.pharmacie_backoffice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.Maladie;
import com.s5.pharmacie_backoffice.models.TraitementMaladie;
import com.s5.pharmacie_backoffice.models.TraitementMaladieId;
import com.s5.pharmacie_backoffice.repositories.TraitementMaladieRepository;

@Service
public class TraitementMaladieService {

    @Autowired 
    private TraitementMaladieRepository traitementMaladieRepository;


    public List<TraitementMaladie> rechercheMedicament(Maladie maladie, int ageMin , int ageMax){
        List<TraitementMaladie> tM = traitementMaladieRepository.findByMaladie(maladie);
        tM = filtreMedicament(ageMin, ageMax, tM);

        return tM;

    }

    public List<TraitementMaladie> filtreMedicament(int ageMin, int ageMax, List<TraitementMaladie> maladies){
        List<TraitementMaladie> tM = new ArrayList<>();
        for(TraitementMaladie maladie : maladies){
            if(maladie.getMedicamentFiche().getAgeMin() >= ageMin && maladie.getMedicamentFiche().getAgeMax() <= ageMax ){
                tM.add(maladie);
            }

        }
 
        return tM;

    }


    public Page<TraitementMaladie> recupererTraitementMaladies(int page, int size, Maladie maladie) {
        try {
            return traitementMaladieRepository.findAllByMaladie(PageRequest.of(page, size), maladie);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des traitements du maladie : " + e.getMessage());
        }
    }

    public TraitementMaladie recupererTraitementMaladie(TraitementMaladieId id) {
        try {
            Optional<TraitementMaladie> traitementOpt = traitementMaladieRepository.findById(id);
            if (traitementOpt.isPresent()) {
                return traitementOpt.get();
            } else {
                throw new RuntimeException("Traitement du maladie non trouvée avec l'ID : " + id.getIdMaladie() + " et " + id.getIdMedicamentFiche());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du traitement du maladie avec l'ID : " + id + " : " + e.getMessage());
        }
    }

    public void sauvegarderTraitementMaladie(TraitementMaladie traitementMaladie) {
        try {
            traitementMaladieRepository.save(traitementMaladie);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du traitement de la maladie : " + e.getMessage());
        }
    }

    public void supprimerTraitementMaladie(TraitementMaladieId id) {
        try {
            traitementMaladieRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du traitement de la maladie avec l'ID : " + id + " : " + e.getMessage());
        }
    }

}
