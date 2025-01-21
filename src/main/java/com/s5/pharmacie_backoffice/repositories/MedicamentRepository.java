package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Medicament;

@SuppressWarnings("null")
@Repository
public interface MedicamentRepository extends JpaRepository<Medicament,Long> {
     Page<Medicament> findAll( Pageable pageable);
}
