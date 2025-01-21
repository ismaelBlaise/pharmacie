package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Recommandation;
import java.sql.Date;
import java.util.List;


@Repository
public interface RecommandationRepository extends JpaRepository<Recommandation,Long> {
    List<Recommandation> findByDateDebut(Date dateDebut);  
      
    @Query("SELECT r FROM Recommandation r WHERE EXTRACT(YEAR FROM r.dateDebut) = :annee")
    List<Recommandation> findByYear(@Param("annee") String annee);


}
