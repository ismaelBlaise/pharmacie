package com.s5.pharmacie_backoffice.models;

import java.sql.Date;

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
@Table(name="utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateurs")
    private Long idUtilisateur;

    @Column(name="nom")
    private String nom;

    @Column(name="prenoms")
    private String prenom;

    @Column(name="email")
    private String email;

    @Column(name = "adresse")
    private String adresse;

    @Column(name="mdp")
    private String mdp;

    @Column(name="poids")
    private Integer poids;

    @Column(name="date_naissance")
    private Date dateNaissance;

    @ManyToOne
    @JoinColumn(name = "id_roles")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id_sexes")
    private Sexe sexe;


    
}