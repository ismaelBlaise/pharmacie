package com.s5.pharmacie_backoffice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_commandes")
    private Long id;

    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    @Column(name = "prix_total", nullable = false)
    private Double prixTotal;

    @ManyToOne
    @JoinColumn(name = "Id_statut_commande", nullable = false)
    private StatutCommande statutCommande;

    @ManyToOne
    @JoinColumn(name = "Id_paniers", nullable = true)
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "Id_utilisateurs", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "Id_utilisateurs_1", nullable = false)
    private Utilisateur utilisateurVendeur;

    @Transient
    private double commission;
}
