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
@Table(name="mouvements_stock")
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_mouvements_stock")
    private Long idMouvementStock; 

    @Column(name="quantite")
    private Integer quantite;

    @Column(name = "date_mouvement")
    private Date dateMouvement;

    @ManyToOne
    @JoinColumn(name = "id_types_mouvement")
    private TypeMouvement typeMouvement;

    @ManyToOne
    @JoinColumn(name = "id_stocks")
    private Stock stock;
    
}
