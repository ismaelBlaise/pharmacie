package com.s5.pharmacie_backoffice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Config;

@Repository
public interface ConfigRepository extends JpaRepository<Config,Long> {
    Optional<Config> findByCle(String cle);
}
