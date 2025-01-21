package com.s5.pharmacie_backoffice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.Forme;
import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.repositories.MedicamentFicheRepository;

@Service
public class MedicamentFicheService {
    @Autowired
    private MedicamentFicheRepository medicamentFicheRepository;

    public List<MedicamentFiche> recupererMedicamentFiches() {
        try {
            return medicamentFicheRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des fiches de médicaments " +e.getMessage(), e);
        }
    }

    public MedicamentFiche recupererMedicamentFiche(Long id) {
        try {
            Optional<MedicamentFiche> ficheOptional = medicamentFicheRepository.findById(id);
            if (ficheOptional.isPresent()) {
                return ficheOptional.get();
            } else {
                throw new RuntimeException("Fiche de médicament non trouvée avec l'ID : " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de la fiche de médicament avec l'ID : " + id +" "+e.getMessage(), e);
        }
    }

    public Page<MedicamentFiche> recupererMedicamentFiches(int page, int size) {
        try {
            return medicamentFicheRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des fiches de médicaments avec pagination "+e.getMessage(), e);
        }
    }

    public void sauvegarderMedicamentFiche(MedicamentFiche fiche) {
        try {
            medicamentFicheRepository.save(fiche);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de la fiche de médicament " +e.getMessage(), e);
        }
    }

    public void supprimerMedicamentFiche(Long id) {
        try {
            medicamentFicheRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de la fiche de médicament avec l'ID : " + id +" "+e.getMessage(), e);
        }
    }

    public List<MedicamentFiche> filtrerByForme(List<Forme> formes){
        List<MedicamentFiche> medicamentFiches=new ArrayList<>();
        for(Forme forme:formes){
            medicamentFiches.addAll(medicamentFicheRepository.findByForme(forme));
        }
        return medicamentFiches;
    }

    public List<MedicamentFiche> filtrerParTrancheAge(List<MedicamentFiche> medicamentFiches,int ageMin,int ageMax){
        List<MedicamentFiche> medicamentFiches2=new ArrayList<>();
        for(MedicamentFiche medicamentFiche:medicamentFiches){
            if(medicamentFiche.getAgeMin()>=ageMin && medicamentFiche.getAgeMax()<=ageMax){
                medicamentFiches2.add(medicamentFiche);
            }
        }
        return medicamentFiches2;
    }
}
