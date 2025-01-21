package com.s5.pharmacie_backoffice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.Maladie;
import com.s5.pharmacie_backoffice.models.MaladieSymptome;
import com.s5.pharmacie_backoffice.models.MaladieSymptomeId;
import com.s5.pharmacie_backoffice.repositories.MaladieSymptomeRepository;

@Service
public class MaladieSymptomeService {

    @Autowired
    private MaladieSymptomeRepository maladieSymptomeRepository;


    public Page<MaladieSymptome> recupererMaladieSymptomes(int page, int size, Maladie maladie) {
        try {
            return maladieSymptomeRepository.findByMaladie(PageRequest.of(page, size), maladie);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des symptomes du maladie : " + e.getMessage());
        }
    }

    public MaladieSymptome recupererMaladieSymptome(MaladieSymptomeId id) {
        try {
            Optional<MaladieSymptome> maladieOptional = maladieSymptomeRepository.findById(id);
            if (maladieOptional.isPresent()) {
                return maladieOptional.get();
            } else {
                throw new RuntimeException("Symptome du maladie non trouvée avec l'ID : " + id.getIdMaladie() + " et " + id.getIdSymptome());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du symptome du maladie avec l'ID : " + id + " : " + e.getMessage());
        }
    }

    public void sauvegarderMaladieSymptome(MaladieSymptome maladieSymptome) {
        try {
            maladieSymptomeRepository.save(maladieSymptome);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du symptome du maladie : " + e.getMessage());
        }
    }

    public void supprimerMaladieSymptome(MaladieSymptomeId id) {
        try {
            maladieSymptomeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du symptome du maladie avec l'ID : " + id + " : " + e.getMessage());
        }
    }
}
