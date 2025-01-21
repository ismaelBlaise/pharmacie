package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.Laboratoire;
import com.s5.pharmacie_backoffice.repositories.LaboratoireRepository;

@Service
public class LaboratoireService {
    @Autowired
    private LaboratoireRepository laboratoireRepository;

    public List<Laboratoire> recupererLaboratoires() {
        try {
            return laboratoireRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des laboratoires", e);
        }
    }

    public Laboratoire recupererLaboratoire(Long id) {
        try {
            Optional<Laboratoire> laboratoireOptional = laboratoireRepository.findById(id);
            if (laboratoireOptional.isPresent()) {
                return laboratoireOptional.get();
            } else {
                throw new RuntimeException("Laboratoire non trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du laboratoire avec l'ID : " + id + " " + e.getMessage());
        }
    }

    public Page<Laboratoire> recupererLaboratoires(int page, int size) {
        try {
            return laboratoireRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des laboratoires avec pagination " + e.getMessage());
        }
    }

    public void sauvegarderLaboratoire(Laboratoire laboratoire) {
        try {
            laboratoireRepository.save(laboratoire);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du laboratoire " + e.getMessage());
        }
    }

    public void supprimerLaboratoire(Long id) {
        try {
            laboratoireRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du laboratoire avec l'ID : " + id + " " + e.getMessage());
        }
    }
}
