package com.s5.pharmacie_backoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.s5.pharmacie_backoffice.models.Role;
import com.s5.pharmacie_backoffice.services.RoleService;


@Controller
@RequestMapping("/roles")
public class RoleController {

    @Autowired 
    private RoleService roleService;

    @GetMapping
    public ModelAndView recupererRoles(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<Role> roles = roleService.recupererRoles(page, size);

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "role/liste");
        modelAndView.addObject("roles", roles.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", roles.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form")
    public ModelAndView ajoutForm() {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "role/ajout");
        return modelAndView;
    }
    

    @PostMapping
    public ModelAndView ajouterRole(
            @RequestParam String role) {

        try {
            Role roleObj = new Role();
            roleObj.setRole(role);
            roleService.sauvegarderRole(roleObj);
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "role/ajout");
            modelAndView.addObject("success", "role ajouter avec succes");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "role/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }


    @PostMapping("/modifier")
    public ModelAndView modifierRole(@RequestParam Long idRole,@RequestParam String role) {

            try {
                Role roleObj = new Role();
                roleObj.setIdRole(idRole);
                roleObj.setRole(role);
                roleService.sauvegarderRole(roleObj);
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "role/modif");
                modelAndView.addObject("role", roleService.recupererRole(idRole));
                modelAndView.addObject("success", "role modifier avec succes");
                return modelAndView;
    
            } catch (Exception e) {
                ModelAndView modelAndView = new ModelAndView("template");
                modelAndView.addObject("page", "role/modif");
                modelAndView.addObject("error", e.getMessage());
                return modelAndView;
            }
    }

    @GetMapping("/supprimer/{id}")
    public ModelAndView supprimerRole(@PathVariable Long id
    , @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            roleService.supprimerRole(id);
            return new ModelAndView("redirect:/roles");
        } catch (Exception e) {
            Page<Role> roles = roleService.recupererRoles(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "role/liste");
            modelAndView.addObject("roles", roles.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", roles.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{id}")
    public ModelAndView modifierFormulaire(@PathVariable Long id,@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
        try {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("role", roleService.recupererRole(id));
            modelAndView.addObject("page", "role/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<Role> roles = roleService.recupererRoles(page, size);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "role/liste");
            modelAndView.addObject("roles", roles.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", roles.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
