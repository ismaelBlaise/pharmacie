package com.s5.pharmacie_backoffice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "paniers")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_paniers")
    private Long id;

    @Column(name = "etat", nullable = false)
    private Boolean etat;

    @Column(name = "date_panier", nullable = false)
    private LocalDate datePanier;

    @ManyToOne
    @JoinColumn(name = "Id_utilisateurs", nullable = false)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailPanier> detailsPanier;
}
