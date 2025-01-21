package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.s5.pharmacie_backoffice.models.Symptome;
import com.s5.pharmacie_backoffice.repositories.SymptomeRepository;

@Service
public class SymptomeService {
    @Autowired
    private SymptomeRepository symptomeRepository;

    public List<Symptome> recupererSymptomes() {
        try {
            return symptomeRepository.findAll();
        } catch (Exception e) {

            throw new RuntimeException("Erreur lors de la récupération des symptomes", e);
        }
    }

    public Page<Symptome> recupererSymptomes(int page, int size) {
        try {
            return symptomeRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des symptomes avec pagination", e);
        }
    }

    public Symptome recupererSymptome(Long id) {
        try {
            Optional<Symptome> syOpt = symptomeRepository.findById(id);
            if (syOpt.isPresent()) {
                return syOpt.get();
            } else {
                throw new RuntimeException("Symptome non trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du symptome avec l'ID : " + id +""+e.getMessage());
        }
    }

    public void sauvegarderSymptome(Symptome symptome) {
        try {
            symptomeRepository.save(symptome);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du symptome "+e.getMessage() );
        }
    }

    public void supprimerSymptome(Long id) {
        try {
            symptomeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du symptome avec l'ID : " + id +""+e.getMessage());
        }
    }
}
