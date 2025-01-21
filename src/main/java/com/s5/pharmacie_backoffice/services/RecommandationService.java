package com.s5.pharmacie_backoffice.services;

import org.springframework.stereotype.Service;

import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.models.Recommandation;
import com.s5.pharmacie_backoffice.repositories.MedicamentFicheRepository;
import com.s5.pharmacie_backoffice.repositories.RecommandationRepository;
import com.s5.pharmacie_backoffice.utils.DateUtil;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class RecommandationService {
    @Autowired
    private RecommandationRepository recommandationRepository;

    @Autowired
    private MedicamentFicheRepository medicamentFicheRepository;

    public void ajouterRecommandation(Long idMedicamentFiche,String raison,Date dateAjout) throws Exception{
        Date[] intervalleDate=DateUtil.recupererDebutFinMois(dateAjout);
        MedicamentFiche medicamentFiche=medicamentFicheRepository.findById(idMedicamentFiche).get();
        Recommandation recommandation=new Recommandation();
        recommandation.setMedicamentFiche(medicamentFiche);
        recommandation.setRaison(raison);
        recommandation.setDateDebut(intervalleDate[0]);
        recommandation.setDateFin(intervalleDate[1]);
        recommandation.setDateAjout(dateAjout);

        List<Recommandation> recommandations=recommandationRepository.findByDateDebut(intervalleDate[0]);
        for(Recommandation re:recommandations){
            if(re.getMedicamentFiche().getIdMedicamentFiche()==idMedicamentFiche){
                throw new Exception("Le medicament est deja recommander en ce mois");
            }
        }
        recommandationRepository.save(recommandation);
        
    }

    public List<Recommandation> rechercherRecommandation(Date date){
        Date[] intervalleDate=DateUtil.recupererDebutFinMois(date);
        List<Recommandation> recommandations=recommandationRepository.findByDateDebut(intervalleDate[0]);
        return recommandations;
    }

    public List<Recommandation> getRecommandationsByYear(String annee) {
        return recommandationRepository.findByYear(annee);
}

}
