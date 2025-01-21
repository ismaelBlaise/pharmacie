package com.s5.pharmacie_backoffice.models;

import java.util.Date;

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
@Table(name="recommandations")
public class Recommandation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recommandations")
    private Long idrecommandations; 

    @Column(name="raison")
    private String raison;

    @Column(name="date_debut")
    private Date dateDebut;

    @Column(name="date_fin")
    private Date dateFin;

    @Column(name="date_ajout")
    private Date dateAjout;
    
    @ManyToOne
    @JoinColumn(name = "Id_medicament_fiches ")
    private MedicamentFiche medicamentFiche;
}
