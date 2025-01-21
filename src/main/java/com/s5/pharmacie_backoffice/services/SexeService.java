package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.s5.pharmacie_backoffice.models.Sexe;
import com.s5.pharmacie_backoffice.repositories.SexeRepository;

@Service
public class SexeService {
    @Autowired
    private SexeRepository sexeRepository;

    public List<Sexe> recupererSexes() {
        try {
            return sexeRepository.findAll();
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération des sexes", e);
        }
    }

    public Sexe recupererSexe(Long id) {
        try {
            Optional<Sexe> sexeOptional = sexeRepository.findById(id);
            if (sexeOptional.isPresent()) {
                return sexeOptional.get();
            } else {
                throw new RuntimeException("Sexe non trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
             
            throw new RuntimeException("Erreur lors de la récupération du sexe avec l'ID : "+ id +""+e.getMessage());
        }
    }

    public Page<Sexe> recupererSexes(int page, int size) {
        try {
            return sexeRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des sexes avec pagination "+e.getMessage());
        }
    }

    public void sauvegarderSexe(Sexe sexe) {
        try {
            sexeRepository.save(sexe);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du sexe "+e.getMessage());
        }
    }

    public void supprimerSexe(Long id) {
        try {
            sexeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du sexe avec l'ID : " + id +""+e.getMessage());
        }
    }
}
