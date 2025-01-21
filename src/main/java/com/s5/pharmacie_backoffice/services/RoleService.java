package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.s5.pharmacie_backoffice.models.Role;
import com.s5.pharmacie_backoffice.repositories.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> recupererRoles() {
        try {
            return roleRepository.findAll();
        } catch (Exception e) {

            throw new RuntimeException("Erreur lors de la récupération des rôles", e);
        }
    }

    public Page<Role> recupererRoles(int page, int size) {
        try {
            return roleRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des roles avec pagination", e);
        }
    }

    public Role recupererRole(Long id) {
        try {
            Optional<Role> roleOptional = roleRepository.findById(id);
            if (roleOptional.isPresent()) {
                return roleOptional.get();
            } else {
                throw new RuntimeException("Role non trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération du rôle avec l'ID : " + id +""+e.getMessage());
        }
    }

    public void sauvegarderRole(Role role) {
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du role "+e.getMessage() );
        }
    }

    public void supprimerRole(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du role avec l'ID : " + id +""+e.getMessage());
        }
    }
}
