package com.s5.pharmacie_backoffice.models;

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
@Table(name = "maladies_symptomes")
public class MaladieSymptome {

    @EmbeddedId
    private MaladieSymptomeId id;

    @ManyToOne
    @MapsId("idMaladie")
    @JoinColumn(name = "Id_maladies", referencedColumnName = "Id_maladies", nullable = false)
    private Maladie maladie;

    @ManyToOne
    @MapsId("idSymptome")
    @JoinColumn(name = "Id_symptomes", referencedColumnName = "Id_symptomes", nullable = false)
    private Symptome symptome;
    
    @Column(name="gravite")
    private Integer gravite;

    @Column(name="frequence")
    private Integer frequence;


}
