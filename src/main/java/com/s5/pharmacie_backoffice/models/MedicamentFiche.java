package com.s5.pharmacie_backoffice.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medicament_fiches")
public class MedicamentFiche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicament_fiches")
    private Long idMedicamentFiche;

    @Column(name="photo")
    private String photo;

    @Column(name="dosage")
    private BigDecimal dosage;

    @Column(name="age_min")
    private Integer ageMin;

    @Column(name="age_max")
    private Integer ageMax;

    @Column(name="poid_min")
    private BigDecimal poidMin;

    @Column(name="poid_max")
    private BigDecimal poidMax;

    @Column(name = "indication")
    private String indication;

    @ManyToOne
    @JoinColumn(name = "Id_formes ")
    private Forme forme;

    @ManyToOne
    @JoinColumn(name = "Id_types_medicament ")
    private TypeMedicament typeMedicament;

    @ManyToOne
    @JoinColumn(name = "Id_medicaments ")
    private Medicament medicament;


}
