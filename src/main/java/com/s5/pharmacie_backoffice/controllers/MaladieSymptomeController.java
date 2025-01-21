package com.s5.pharmacie_backoffice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.s5.pharmacie_backoffice.models.MaladieSymptome;
import com.s5.pharmacie_backoffice.models.MaladieSymptomeId;
import com.s5.pharmacie_backoffice.services.MaladieService;
import com.s5.pharmacie_backoffice.services.MaladieSymptomeService;
import com.s5.pharmacie_backoffice.services.SymptomeService;

@Controller
@RequestMapping("/maladies-symptomes")
public class MaladieSymptomeController {
    @Autowired
    private MaladieService maladieService;

    @Autowired
    private SymptomeService symptomeService;

    @Autowired
    private MaladieSymptomeService maladieSymptomeService;

    @PostMapping("/recuperer")
    public ModelAndView recupererMaladieSymptomes(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long idMaladie
    ) {
        Page<MaladieSymptome> maladieSymptomes = maladieSymptomeService.recupererMaladieSymptomes(page, size, maladieService.recupererMaladie(idMaladie));

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "maladie-symptome/liste");
        modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
        modelAndView.addObject("maladieSymptomes", maladieSymptomes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", maladieSymptomes.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/recuperer")
    public ModelAndView recupererMaladieSymptomes2(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long idMaladie
    ) {
        Page<MaladieSymptome> maladieSymptomes = maladieSymptomeService.recupererMaladieSymptomes(page, size, maladieService.recupererMaladie(idMaladie));

        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("page", "maladie-symptome/liste");
        modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
        modelAndView.addObject("maladieSymptomes", maladieSymptomes.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", maladieSymptomes.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/ajout-form/{idMaladie}")
    public ModelAndView ajoutForm(@PathVariable Long idMaladie) {
        ModelAndView modelAndView = new ModelAndView("template");
        modelAndView.addObject("symptomes",symptomeService.recupererSymptomes());       
        modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
        modelAndView.addObject("page", "maladie-symptome/ajout");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView ajouterMaladieSymptome(
            @RequestParam Long idMaladie,
            @RequestParam Long idSymptome,
            @RequestParam Integer gravite,
            @RequestParam Integer frequence) {

        try {
            MaladieSymptomeId maladieSymptomeId = new MaladieSymptomeId();
            maladieSymptomeId.setIdMaladie(idMaladie);
            maladieSymptomeId.setIdSymptome(idSymptome);
            MaladieSymptome maladieSymptome = new MaladieSymptome();
            maladieSymptome.setId(maladieSymptomeId);
            maladieSymptome.setMaladie(maladieService.recupererMaladie(idMaladie));
            maladieSymptome.setSymptome(symptomeService.recupererSymptome(idSymptome));
            maladieSymptome.setGravite(gravite);
            maladieSymptome.setFrequence(frequence);

            maladieSymptomeService.sauvegarderMaladieSymptome(maladieSymptome);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie-symptome/ajout");
            modelAndView.addObject("symptomes",symptomeService.recupererSymptomes());       
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("success", "Symptome maladie ajoutée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("symptomes",symptomeService.recupererSymptomes());       
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("page", "maladie-symptome/ajout");
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/modifier")
    public ModelAndView modifierMaladieSymptome(@RequestParam Long idSymptome,
            @RequestParam Long idMaladie,
            @RequestParam Integer gravite,
            @RequestParam Integer frequence) {

                MaladieSymptomeId maladieSymptomeId = new MaladieSymptomeId();
            maladieSymptomeId.setIdMaladie(idMaladie);
            maladieSymptomeId.setIdSymptome(idSymptome);
        try {
            
            
            MaladieSymptome maladieSymptome = new MaladieSymptome();
            maladieSymptome.setId(maladieSymptomeId);
            maladieSymptome.setMaladie(maladieService.recupererMaladie(idMaladie));
            maladieSymptome.setSymptome(symptomeService.recupererSymptome(idSymptome));
            maladieSymptome.setGravite(gravite);
            maladieSymptome.setFrequence(frequence);

            
            maladieSymptomeService.sauvegarderMaladieSymptome(maladieSymptome);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie-symptome/ajout");
            modelAndView.addObject("symptomes",symptomeService.recupererSymptomes());       
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("maladieSymptome", maladieSymptomeService.recupererMaladieSymptome(maladieSymptomeId));
            modelAndView.addObject("success", "Symptome maladie modifiée avec succès");
            return modelAndView;

        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie-symptome/modif");
            modelAndView.addObject("symptomes",symptomeService.recupererSymptomes());       
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("maladieSymptome", maladieSymptomeService.recupererMaladieSymptome(maladieSymptomeId));
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/supprimer/{idSymptome}/{idMaladie}")
    public ModelAndView supprimerMaladieSymptome(@PathVariable Long idSymptome,@PathVariable Long idMaladie,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            MaladieSymptomeId maladieSymptomeId = new MaladieSymptomeId();
            maladieSymptomeId.setIdMaladie(idMaladie);
            maladieSymptomeId.setIdSymptome(idSymptome);
            maladieSymptomeService.supprimerMaladieSymptome(maladieSymptomeId); 
            Page<MaladieSymptome> maladieSymptomes = maladieSymptomeService.recupererMaladieSymptomes(page, size, maladieService.recupererMaladie(idMaladie));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie-symptome/liste");
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("maladieSymptomes", maladieSymptomes.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", maladieSymptomes.getTotalPages());
            return modelAndView;
        } catch (Exception e) {
            Page<MaladieSymptome> maladieSymptomes = maladieSymptomeService.recupererMaladieSymptomes(page, size, maladieService.recupererMaladie(idMaladie));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie-symptome/liste");
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("maladieSymptomes", maladieSymptomes.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", maladieSymptomes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }

    @GetMapping("/modifier-form/{idSymptome}/{idMaladie}")
    public ModelAndView modifierFormulaire(@PathVariable Long idSymptome,@PathVariable Long idMaladie,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            MaladieSymptomeId maladieSymptomeId = new MaladieSymptomeId();
            maladieSymptomeId.setIdMaladie(idMaladie);
            maladieSymptomeId.setIdSymptome(idSymptome);

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("symptomes",symptomeService.recupererSymptomes());       
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("maladieSymptome", maladieSymptomeService.recupererMaladieSymptome(maladieSymptomeId));
            modelAndView.addObject("page", "maladie-symptome/modif");
            return modelAndView;
        } catch (Exception e) {
            Page<MaladieSymptome> maladieSymptomes = maladieSymptomeService.recupererMaladieSymptomes(page, size, maladieService.recupererMaladie(idMaladie));

            ModelAndView modelAndView = new ModelAndView("template");
            modelAndView.addObject("page", "maladie-symptome/liste");
            modelAndView.addObject("maladie", maladieService.recupererMaladie(idMaladie));
            modelAndView.addObject("maladieSymptomes", maladieSymptomes.getContent());
            modelAndView.addObject("currentPage", page);
            modelAndView.addObject("totalPages", maladieSymptomes.getTotalPages());
            modelAndView.addObject("error", e.getMessage());
            return modelAndView;
        }
    }
}
