package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Sexe;
@SuppressWarnings("null")
@Repository
public interface SexeRepository extends JpaRepository<Sexe,Long> {
    Page<Sexe> findAll(Pageable pageable);
}
