package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.Forme;
import com.s5.pharmacie_backoffice.repositories.FormeRepository;

@Service
public class FormeService {
    @Autowired
    private FormeRepository formeRepository;

    public List<Forme> recupererFormes() {
        try {
            return formeRepository.findAll();
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération des formes", e);
        }
    }

    public Forme recupererForme(Long id) {
        try {
            Optional<Forme> formeOptional = formeRepository.findById(id);
            if (formeOptional.isPresent()) {
                return formeOptional.get();
            } else {
                throw new RuntimeException("Forme non trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération du forme avec l'ID : "+ id +""+e.getMessage());
        }
    }

    public Page<Forme> recupererFormes(int page, int size) {
        try {
            return formeRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des formes avec pagination "+e.getMessage());
        }
    }

    public void sauvegarderForme(Forme forme) {
        try {
            formeRepository.save(forme);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du forme "+e.getMessage());
        }
    }

    public void supprimerForme(Long id) {
        try {
            formeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du forme avec l'ID : " + id +""+e.getMessage());
        }
    }
}
