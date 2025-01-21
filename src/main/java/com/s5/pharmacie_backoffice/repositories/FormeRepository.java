package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.s5.pharmacie_backoffice.models.Forme;
import com.s5.pharmacie_backoffice.models.VoieAdministration;

import java.util.List;


@SuppressWarnings("null")
@Repository
public interface FormeRepository  extends JpaRepository<Forme,Long>{
    Page<Forme> findAll( Pageable pageable);

    List<Forme> findByVoieAdministration(VoieAdministration voieAdministration);
}
