package com.s5.pharmacie_backoffice.controllers;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.s5.pharmacie_backoffice.models.Utilisateur;
import com.s5.pharmacie_backoffice.services.RoleService;
import com.s5.pharmacie_backoffice.services.SexeService;
import com.s5.pharmacie_backoffice.services.UtilisateurService;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {
    
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private SexeService sexeService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView recupererUtilisateurs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Utilisateur> utilisateurs = utilisateurService.recupererUtilisateurs(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "utilisateur/liste");
        modelAndView.addObject("utilisateurs", utilisateurs.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", utilisateurs.getTotalPages());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterUtilisateur(
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String adresse,
            @RequestParam String mdp,
            @RequestParam int poids,
            @RequestParam Date dateNaissance,
            @RequestParam Long role,
            @RequestParam Long sexe) {

        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setAdresse(adresse);
            utilisateur.setMdp(mdp);
            utilisateur.setPoids(poids);
            utilisateur.setDateNaissance(dateNaissance);
            utilisateur.setRole(roleService.recupererRole(role));
            utilisateur.setSexe(sexeService.recupererSexe(sexe));

            utilisateurService.ajouterUtilisateur(utilisateur);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/ajout");
            modelAndView.addObject("sexes", sexeService.recupererSexes());
            modelAndView.addObject("roles", roleService.recupererRoles());
            modelAndView.addObject("success", "Utilisateur ajouter avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/ajout");
            modelAndView.addObject("sexes", sexeService.recupererSexes());
            modelAndView.addObject("roles", roleService.recupererRoles());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierUtilisateur(
            @RequestParam Long idUtilisateur,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String adresse,
            @RequestParam(defaultValue = "") String mdp,
            @RequestParam int poids,
            @RequestParam Date dateNaissance,
            @RequestParam Long role,
            @RequestParam Long sexe) {

        try {
            Utilisateur utilisateur =  utilisateurService.recupererUtilisateur(idUtilisateur);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setAdresse(adresse);

            // System.out.println("\n\n\n\n "+mdp+" \n\n\n\n"); 

            if (!mdp.isEmpty()) {
                String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt(10));
                utilisateur.setMdp(hashedPassword);
            }
            
            utilisateur.setPoids(poids);
            utilisateur.setDateNaissance(dateNaissance);
            utilisateur.setRole(roleService.recupererRole(role));
            utilisateur.setSexe(sexeService.recupererSexe(sexe));

            utilisateurService.modifierUtilisateur(utilisateur);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/modif");
            modelAndView.addObject("sexes", sexeService.recupererSexes());
            modelAndView.addObject("utilisateur", utilisateurService.recupererUtilisateur(idUtilisateur));
            modelAndView.addObject("roles", roleService.recupererRoles());
            modelAndView.addObject("success", "Utilisateur modifier avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/modif");
            modelAndView.addObject("sexes", sexeService.recupererSexes());
            modelAndView.addObject("roles", roleService.recupererRoles());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier2")
    public ModelAndView modifierUtilisateur2(
            @RequestParam Long idUtilisateur,
            @RequestParam String nom,
            @RequestParam String prenom,
            @RequestParam String email,
            @RequestParam String adresse,
            @RequestParam(defaultValue = "") String mdp,
            @RequestParam int poids,
            @RequestParam Date dateNaissance,
            @RequestParam Long role,
            @RequestParam Long sexe) {

        try {
            Utilisateur utilisateur =  utilisateurService.recupererUtilisateur(idUtilisateur);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setAdresse(adresse);


            if (!mdp.isEmpty()) {
                String hashedPassword = BCrypt.hashpw(mdp, BCrypt.gensalt(10));
                utilisateur.setMdp(hashedPassword);
            }
            
            utilisateur.setPoids(poids);
            utilisateur.setDateNaissance(dateNaissance);
            utilisateur.setRole(roleService.recupererRole(role));
            utilisateur.setSexe(sexeService.recupererSexe(sexe));

            utilisateurService.modifierUtilisateur(utilisateur);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/profil");
            modelAndView.addObject("sexes", sexeService.recupererSexes());
            modelAndView.addObject("utilisateur", utilisateurService.recupererUtilisateur(idUtilisateur));
            modelAndView.addObject("roles", roleService.recupererRoles());
            modelAndView.addObject("success", "Utilisateur modifier avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/profil");
            modelAndView.addObject("sexes", sexeService.recupererSexes());
            modelAndView.addObject("roles", roleService.recupererRoles());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "utilisateur/ajout");
        modelAndView.addObject("sexes", sexeService.recupererSexes());
        modelAndView.addObject("roles", roleService.recupererRoles());
        return modelAndView;
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerUtilisateur(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            utilisateurService.supprimerUtilisateur(id);
            return new ModelAndView("redirect:/utilisateurs");
        } catch (Exception e) {

            Page<Utilisateur> utilisateurs = utilisateurService.recupererUtilisateurs(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/liste");
            modelAndView.addObject("utilisateurs", utilisateurs.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", utilisateurs.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/login")
    public ModelAndView connexionPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/connexion")
    public ModelAndView connexion(@RequestParam String email,
                                  @RequestParam String password,
                                  HttpSession session) {
        try {
            Utilisateur utilisateur = utilisateurService.connexion(email, password);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("utilisateur", utilisateur);
            session.setAttribute("utilisateur", utilisateur);
            modelAndView.addObject("page", "pages/accueil");
            return modelAndView;
        } catch (RuntimeException e) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/deconnexion")
    public ModelAndView deconnexion(HttpSession session) {
        session.removeAttribute("utilisateur");
        return new ModelAndView("login");
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("utilisateur", utilisateurService.recupererUtilisateur(id));
            modelAndView.addObject("page", "utilisateur/modif");
            modelAndView.addObject("sexes", sexeService.recupererSexes());
            modelAndView.addObject("roles", roleService.recupererRoles());
            return modelAndView;
        } catch (Exception e) {
            Page<Utilisateur> utilisateurs = utilisateurService.recupererUtilisateurs(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "utilisateur/liste");
            modelAndView.addObject("utilisateurs", utilisateurs.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", utilisateurs.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/profil-page")
    public ModelAndView profilPage( HttpSession session){
        ModelAndView modelAndView = new ModelAndView("template");

        modelAndView.addObject("utilisateur",(Utilisateur) session.getAttribute("utilisateur"));;
        modelAndView.addObject("sexes", sexeService.recupererSexes());
        modelAndView.addObject("roles", roleService.recupererRoles());
        modelAndView.addObject("page", "utilisateur/profil");
        return modelAndView;
    }
}
