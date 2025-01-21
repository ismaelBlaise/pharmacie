package com.s5.pharmacie_backoffice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.MedicamentComposition;
import com.s5.pharmacie_backoffice.models.MedicamentCompositionId;
import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.repositories.MedicamentCompositionRepository;

@Service
public class MedicamentCompositionService {

    @Autowired
    private MedicamentCompositionRepository medicamentCompositionRepository;


    public Page<MedicamentComposition> recupererCompositionsMedicament(int page, int size, MedicamentFiche fiche) {
        try {
            return medicamentCompositionRepository.findByMedicamentFiche(PageRequest.of(page, size), fiche);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des compositions du médicament : " + e.getMessage());
        }
    }

    public MedicamentComposition recupererCompositionMedicament(MedicamentCompositionId id) {
        try {
            Optional<MedicamentComposition> compositionOptional = medicamentCompositionRepository.findById(id);
            if (compositionOptional.isPresent()) {
                return compositionOptional.get();
            } else {
                throw new RuntimeException("Composition de médicament non trouvée avec l'ID : " + id.getIdComposition() + " et " + id.getIdMedicamentFiche());
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la composition de médicament avec l'ID : " + id + " : " + e.getMessage());
        }
    }

    public void sauvegarderCompositionMedicament(MedicamentComposition medicamentComposition) {
        try {
            medicamentCompositionRepository.save(medicamentComposition);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la composition de médicament : " + e.getMessage());
        }
    }

    public void supprimerCompositionMedicament(MedicamentCompositionId id) {
        try {
            medicamentCompositionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la composition de médicament avec l'ID : " + id + " : " + e.getMessage());
        }
    }
}
