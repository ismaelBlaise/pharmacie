package com.s5.pharmacie_backoffice.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Maladie;

@Repository
public interface MaladieRepository extends JpaRepository<Maladie,Long> {
    @SuppressWarnings("null")
    Page<Maladie> findAll(Pageable pageable);

    Optional<Maladie> findByMaladie(String maladie);
}
