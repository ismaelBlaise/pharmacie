package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Laboratoire;

@Repository
public interface LaboratoireRepository extends JpaRepository<Laboratoire,Long>{
    @SuppressWarnings("null")
    Page<Laboratoire> findAll(Pageable pageable);
}
    