package com.s5.pharmacie_backoffice.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Forme;
import com.s5.pharmacie_backoffice.models.MedicamentFiche;

@Repository
public interface MedicamentFicheRepository extends JpaRepository<MedicamentFiche,Long> {
    @SuppressWarnings("null")
    Page<MedicamentFiche> findAll(Pageable pageable);

    List<MedicamentFiche> findByForme(Forme forme);
}
