package com.s5.pharmacie_backoffice.services;

import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.s5.pharmacie_backoffice.models.Role;
import com.s5.pharmacie_backoffice.models.Utilisateur;
import com.s5.pharmacie_backoffice.repositories.RoleRepository;
import com.s5.pharmacie_backoffice.repositories.UtilisateurRepository;

@Service
public class UtilisateurService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Utilisateur connexion(String email, String mdp) {
        try {
            Optional<Role> roleOpt = roleRepository.findByRole("Administrateur");
            if (!roleOpt.isPresent()) {
                throw new RuntimeException("Le rôle défini n'existe pas");
            }

            Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
            if (!utilisateurOpt.isPresent()) {
                throw new RuntimeException("L'utilisateur n'existe pas");
            }

            Utilisateur utilisateur = utilisateurOpt.get();
            if (!utilisateur.getRole().equals(roleOpt.get())) {
                throw new RuntimeException("Vous n'avez pas le droit de vous connectez ici");
            }
            if(!BCrypt.checkpw( mdp,utilisateur.getMdp())){
                throw new RuntimeException("Mot de passe incorrect");
            }   
            // if (!utilisateur.getMdp().equals(mdp)) {
            //     throw new RuntimeException("Mot de passe incorrect");
            // }

            utilisateur.setMdp(null);   
            return utilisateur;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Utilisateur> recupererUtilisateurs() {
        try {
            return utilisateurRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs "+e.getMessage());
        }
    }

    public Page<Utilisateur> recupererUtilisateurs(int page, int size) {
        try {
            return utilisateurRepository.findAll(PageRequest.of(page, size));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des utilisateurs avec pagination" +e.getMessage());
        }
    }

    public void supprimerUtilisateur(Long id) {
        try {
            utilisateurRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur avec l'ID : "+ id +""+e.getMessage());
        }
    }

    public Utilisateur recupererUtilisateur(Long id) {
        try {
            Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findById(id);
            if (!utilisateurOpt.isPresent()) {
                throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
            }
            return utilisateurOpt.get();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de l'utilisateur avec l'ID : "+ id +""+e.getMessage());
        }
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        try {
            // String hashedPassword = BCrypt.hashpw(utilisateur.getMdp(), BCrypt.gensalt(10));
            // utilisateur.setMdp(hashedPassword);
            utilisateurRepository.save(utilisateur);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'utilisateur "+e.getMessage());
        }
    }

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        try {
            String hashedPassword = BCrypt.hashpw(utilisateur.getMdp(), BCrypt.gensalt(10));
            utilisateur.setMdp(hashedPassword);
            utilisateurRepository.save(utilisateur);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'utilisateur "+e.getMessage());
        }
    }
    
}
