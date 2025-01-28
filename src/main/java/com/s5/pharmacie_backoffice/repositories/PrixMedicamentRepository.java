package com.s5.pharmacie_backoffice.repositories;

import com.s5.pharmacie_backoffice.models.PrixMedicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrixMedicamentRepository extends JpaRepository<PrixMedicament, Long> {

    // 1. Récupérer le prix le plus récent d'un médicament
    @Query("SELECT p FROM PrixMedicament p WHERE p.medicamentFiche.id = :idMedicamentFiche " +
           "ORDER BY p.dateChangement DESC")
    PrixMedicament findMostRecentPrice(@Param("idMedicamentFiche") Long idMedicamentFiche);

    // @Query("SELECT p FROM PrixMedicament p WHERE p.medicamentFiche.id = :idMedicamentFiche  AND p.dateChangement<=:startDate" +
    // "ORDER BY p.dateChangement DESC")
    // PrixMedicament findMostRecentPrice2(@Param("idMedicamentFiche") Long idMedicamentFiche,@Param("startDate") LocalDate startDate);

    // 2. Récupérer tous les prix associés à un médicament
    @Query("SELECT p FROM PrixMedicament p WHERE p.medicamentFiche.id = :idMedicamentFiche")
    List<PrixMedicament> findAllPricesByMedicament(@Param("idMedicamentFiche") Long idMedicamentFiche);

   @Query("SELECT s FROM PrixMedicament s WHERE s.dateChangement BETWEEN :startDate AND :endDate AND s.medicamentFiche.idMedicamentFiche = :idMedicamentFiche")
    List<PrixMedicament> findByDateRangeAndMedicament(@Param("startDate") LocalDate startDate, 
                                             @Param("endDate") LocalDate endDate, 
                                             @Param("idMedicamentFiche") Long idMedicamentFiche);
}
