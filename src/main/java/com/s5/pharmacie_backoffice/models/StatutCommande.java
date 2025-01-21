package com.s5.pharmacie_backoffice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "statut_commande")
public class StatutCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_statut_commande")
    private Long id;

    @Column(name = "statut", nullable = false, unique = true, length = 50)
    private String statut;
}
