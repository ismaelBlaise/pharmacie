package com.s5.pharmacie_backoffice.repositories;

import com.s5.pharmacie_backoffice.models.StatutCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface StatutCommandeRepository extends JpaRepository<StatutCommande, Long> {

    Optional<StatutCommande> findByStatut(String statut);

}
