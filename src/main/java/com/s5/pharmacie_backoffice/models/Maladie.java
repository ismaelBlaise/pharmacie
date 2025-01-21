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
@Table(name = "maladies")
public class Maladie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maladies")
    private Long idMaladie;

    @Column(name = "maladie")
    private String maladie;


    @Column(name = "description")
    private String description;

}
