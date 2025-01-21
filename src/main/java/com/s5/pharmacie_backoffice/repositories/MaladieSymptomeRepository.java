package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Maladie;
import com.s5.pharmacie_backoffice.models.MaladieSymptome;
import com.s5.pharmacie_backoffice.models.MaladieSymptomeId;

@Repository
public interface MaladieSymptomeRepository extends JpaRepository<MaladieSymptome,MaladieSymptomeId>{
    Page<MaladieSymptome> findByMaladie(Pageable pageable,Maladie maladie);
}
