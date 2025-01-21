package com.s5.pharmacie_backoffice.repositories;

import com.s5.pharmacie_backoffice.models.DetailPanier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailPanierRepository extends JpaRepository<DetailPanier, Long> {
}
