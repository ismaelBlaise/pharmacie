package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.VoieAdministration;
import com.s5.pharmacie_backoffice.repositories.VoieAdministrationRepository;

@Service
public class VoieAdministrationService {
    @Autowired
    private VoieAdministrationRepository voieAdministrationRepository;

    public List<VoieAdministration> recupererVoiesAdministration() {
        try {
            return voieAdministrationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des voies d'administration", e);
        }
    }

    public VoieAdministration recupererVoieAdministration(Long id) {
        try {
            Optional<VoieAdministration> voieOptional = voieAdministrationRepository.findById(id);
            if (voieOptional.isPresent()) {
                return voieOptional.get();
            } else {
                throw new RuntimeException("Voie d'administration non trouvée avec l'ID : " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la voie d'administration avec l'ID : " + id, e);
        }
    }

    public Page<VoieAdministration> recupererVoiesAdministration(int page, int size) {
        try {
            return voieAdministrationRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des voies d'administration avec pagination", e);
        }
    }

    public void sauvegarderVoieAdministration(VoieAdministration voieAdministration) {
        try {
            voieAdministrationRepository.save(voieAdministration);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la voie d'administration", e);
        }
    }

    public void supprimerVoieAdministration(Long id) {
        try {
            voieAdministrationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la voie d'administration avec l'ID : " + id, e);
        }
    }
}
