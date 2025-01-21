package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.TypeMedicament;
import com.s5.pharmacie_backoffice.repositories.TypeMedicamentRepository;

@Service
public class TypeMedicamentService {
    @Autowired
    private TypeMedicamentRepository typeMedicamentRepository;

    public List<TypeMedicament> recupererTypeMedicaments() {
        try {
            return typeMedicamentRepository.findAll();
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération des TypeMedicaments", e);
        }
    }

    public TypeMedicament recupererTypeMedicament(Long id) {
        try {
            Optional<TypeMedicament> typeMedicamentOptional = typeMedicamentRepository.findById(id);
            if (typeMedicamentOptional.isPresent()) {
                return typeMedicamentOptional.get();
            } else {
                throw new RuntimeException("TypeMedicament non trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération du TypeMedicament avec l'ID : "+ id +""+e.getMessage());
        }
    }

    public Page<TypeMedicament> recupererTypeMedicaments(int page, int size) {
        try {
            return typeMedicamentRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des TypeMedicaments avec pagination "+e.getMessage());
        }
    }

    public void sauvegarderTypeMedicament(TypeMedicament typeMedicament) {
        try {
            typeMedicamentRepository.save(typeMedicament);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du TypeMedicament "+e.getMessage());
        }
    }

    public void supprimerTypeMedicament(Long id) {
        try {
            typeMedicamentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du TypeMedicament avec l'ID : " + id +""+e.getMessage());
        }
    }
}
