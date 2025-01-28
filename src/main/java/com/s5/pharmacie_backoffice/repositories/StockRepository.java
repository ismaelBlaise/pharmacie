package com.s5.pharmacie_backoffice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Commande;
import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    @SuppressWarnings("null")
    List<Stock> findByMedicamentFiche(MedicamentFiche medicamentFiche);


    @Query("SELECT s FROM Stock s WHERE s.dateStock BETWEEN :startDate AND :endDate AND s.medicamentFiche.idMedicamentFiche = :idMedicamentFiche")
    List<Stock> findByDateRangeAndMedicament(@Param("startDate") LocalDate startDate, 
                                             @Param("endDate") LocalDate endDate, 
                                             @Param("idMedicamentFiche") Long idMedicamentFiche);
}
