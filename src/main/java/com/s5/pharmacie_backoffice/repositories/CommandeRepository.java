package com.s5.pharmacie_backoffice.repositories;

import com.s5.pharmacie_backoffice.models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    
    List<Commande> findByDateCommande(LocalDate dateCommande);
    
    @Query("SELECT c FROM Commande c WHERE c.dateCommande BETWEEN :startDate AND :endDate AND c.utilisateurVendeur.id = :vendeurId")
    List<Commande> findByDateRangeAndVendeur(@Param("startDate") LocalDate startDate, 
                                             @Param("endDate") LocalDate endDate, 
                                             @Param("vendeurId") Long vendeurId);

    @Query("SELECT c FROM Commande c WHERE c.dateCommande BETWEEN :startDate AND :endDate ")
    List<Commande> findByDateRange(@Param("startDate") LocalDate startDate, 
                                   @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Commande c WHERE c.dateCommande >= :startDate ")
    List<Commande> findByStartDate(@Param("startDate") LocalDate startDate
                                   );

    @Query("SELECT c FROM Commande c WHERE c.dateCommande <= :endDate ")
    List<Commande> findByEndDate(@Param("endDate") LocalDate endDate
                                    );
                                        
}
