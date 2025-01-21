package com.s5.pharmacie_backoffice.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.Role;
@SuppressWarnings("null")
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole(String role);

    Page<Role> findAll(Pageable pageable);
}
