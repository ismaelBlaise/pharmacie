package com.s5.pharmacie_backoffice.models;

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
@Table(name="formes")
public class Forme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formes")
    private Long idForme; 

    @Column(name="forme")
    private String forme;
    
    @ManyToOne
    @JoinColumn(name = "Id_voies_administration ")
    private VoieAdministration voieAdministration;
}
