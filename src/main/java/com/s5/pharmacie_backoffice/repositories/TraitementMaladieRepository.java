package com.s5.pharmacie_backoffice.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Maladie;
import com.s5.pharmacie_backoffice.models.TraitementMaladie;
import com.s5.pharmacie_backoffice.models.TraitementMaladieId;

@Repository
public interface TraitementMaladieRepository extends JpaRepository<TraitementMaladie,TraitementMaladieId> {
    @SuppressWarnings("null")
    Page<TraitementMaladie> findAll(Pageable pageable);

    List<TraitementMaladie> findByMaladie(Maladie maladie);

    Page<TraitementMaladie> findAllByMaladie(Pageable pageable,Maladie maladie);

}



