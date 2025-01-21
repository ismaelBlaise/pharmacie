package com.s5.pharmacie_backoffice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicaments")
    private Long idMedicament;

    @Column(name = "nom_commercial")
    private String nomCommercial;


    @Column(name = "nom_scientifique")
    private String nomScientifique;

}
