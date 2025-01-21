package com.s5.pharmacie_backoffice.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "traitements_maladie")
public class TraitementMaladie {

    @EmbeddedId
    private TraitementMaladieId id;

    @ManyToOne
    @MapsId("idMaladie")
    @JoinColumn(name = "Id_maladies", referencedColumnName = "Id_maladies", nullable = false)
    private Maladie maladie;

    @ManyToOne
    @MapsId("idMedicamentFiche")
    @JoinColumn(name = "Id_medicament_fiches", referencedColumnName = "Id_medicament_fiches", nullable = false)
    private MedicamentFiche medicamentFiche;
    
    @Column(name="dose")
    private BigDecimal dose;

    @Column(name = "frequence")
    private Integer frequence;

    @Column(name = "moment")
    private String moment;

    @Column(name = "duree")
    private BigDecimal duree;

    @Column(name = "instruction")
    private String instruction;


}
