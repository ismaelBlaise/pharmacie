package com.s5.pharmacie_backoffice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "details_panier")
public class DetailPanier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_details_panier")
    private Long id;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @ManyToOne
    @JoinColumn(name = "Id_medicament_fiches", nullable = false)
    private MedicamentFiche medicamentFiche;

    @ManyToOne
    @JoinColumn(name = "Id_paniers", nullable = false)
    private Panier panier;
}
