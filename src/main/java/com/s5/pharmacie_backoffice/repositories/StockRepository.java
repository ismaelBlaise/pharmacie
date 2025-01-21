package com.s5.pharmacie_backoffice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.MedicamentFiche;
import com.s5.pharmacie_backoffice.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    @SuppressWarnings("null")
    List<Stock> findByMedicamentFiche(MedicamentFiche medicamentFiche);
}
