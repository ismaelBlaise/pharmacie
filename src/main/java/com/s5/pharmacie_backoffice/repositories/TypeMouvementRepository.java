package com.s5.pharmacie_backoffice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.TypeMouvement;

@Repository
public interface TypeMouvementRepository extends JpaRepository<TypeMouvement,Long>{
    Optional<TypeMouvement> findByTypeMouvement(String typeMouvement);
}
