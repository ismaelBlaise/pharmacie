package com.s5.pharmacie_backoffice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Utilisateur;
@SuppressWarnings("null")
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    // Optional<Utilisateur> findByRole(Role role);
    Optional<Utilisateur> findByEmail(String email);
    
    Page<Utilisateur> findAll(Pageable pageable);

    @Query("SELECT u FROM Utilisateur u WHERE u.role.role = :roleName")
    List<Utilisateur> findUtilisateursByRoleName(@Param("roleName") String roleName);
}
