package com.s5.pharmacie_backoffice.repositories;

import com.s5.pharmacie_backoffice.models.Panier;
import com.s5.pharmacie_backoffice.models.Utilisateur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    List<Panier> findByUtilisateur(Utilisateur utilisateur);
}
