package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.s5.pharmacie_backoffice.models.Medicament;
import com.s5.pharmacie_backoffice.repositories.MedicamentRepository;


@Service
public class MedicamentService {
    
    @Autowired
    private MedicamentRepository medicamentRepository;

     public List<Medicament> recupererMedicaments() {
        try {
            return medicamentRepository.findAll();
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération des medicaments", e);
        }
    }

    public Medicament recupererMedicament(Long id) {
        try {
            Optional<Medicament> medicamentOptional = medicamentRepository.findById(id);
            if (medicamentOptional.isPresent()) {
                return medicamentOptional.get();
            } else {
                throw new RuntimeException("Medicament non trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération du medicament avec l'ID : "+ id +""+e.getMessage());
        }
    }

    public Page<Medicament> recupererMedicaments(int page, int size) {
        try {
            return medicamentRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des medicaments avec pagination "+e.getMessage());
        }
    }

    public void sauvegarderMedicament(Medicament medicament) {
        try {
            medicamentRepository.save(medicament);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du medicament "+e.getMessage());
        }
    }

    public void supprimerMedicament(Long id) {
        try {
            medicamentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du medicament avec l'ID : " + id +""+e.getMessage());
        }
    }
    
}
