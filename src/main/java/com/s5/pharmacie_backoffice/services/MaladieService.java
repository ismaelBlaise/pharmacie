package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.Maladie;
import com.s5.pharmacie_backoffice.repositories.MaladieRepository;

@Service
public class MaladieService {
    @Autowired
    private MaladieRepository maladieRepository;

    public Page<Maladie> recupererMaladies(int page, int size) {
        try {
            return maladieRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des Maladies : " + e.getMessage());
        }
    }

    public List<Maladie> recupererMaladies() {
        try {
            return maladieRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des Maladies : " + e.getMessage());
        }
    }

    public Maladie recupererMaladie(Long id) {
        try {
            Optional<Maladie> maladieOptional = maladieRepository.findById(id);
            if (maladieOptional.isPresent()) {
                return maladieOptional.get();
            } else {
                throw new RuntimeException("Maladie non trouvée avec l'ID : " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la Maladie avec l'ID : " + id + " : " + e.getMessage());
        }
    }

    public void sauvegarderMaladie(Maladie maladie) {
        try {
            maladieRepository.save(maladie);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la Maladie : " + e.getMessage());
        }
    }

    public void supprimerMaladie(Long id) {
        try {
            maladieRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la Maladie avec l'ID : " + id + " : " + e.getMessage());
        }
    }
}
