package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.s5.pharmacie_backoffice.models.Composition;
import com.s5.pharmacie_backoffice.repositories.CompositionRepository;

@Service
public class CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;

    public Page<Composition> recupererCompositions(int page, int size) {
        try {
            return compositionRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des compositions : " + e.getMessage());
        }
    }

    public List<Composition> recupererCompositions() {
        try {
            return compositionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des compositions : " + e.getMessage());
        }
    }

    public Composition recupererComposition(Long id) {
        try {
            Optional<Composition> compositionOptional = compositionRepository.findById(id);
            if (compositionOptional.isPresent()) {
                return compositionOptional.get();
            } else {
                throw new RuntimeException("Composition non trouvée avec l'ID : " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la composition avec l'ID : " + id + " : " + e.getMessage());
        }
    }

    public void sauvegarderComposition(Composition composition) {
        try {
            compositionRepository.save(composition);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la composition : " + e.getMessage());
        }
    }

    public void supprimerComposition(Long id) {
        try {
            compositionRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la composition avec l'ID : " + id + " : " + e.getMessage());
        }
    }
}
