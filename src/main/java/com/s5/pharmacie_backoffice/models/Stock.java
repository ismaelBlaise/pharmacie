package com.s5.pharmacie_backoffice.models;

import java.math.BigDecimal;
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
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_stocks")
    private Long idStock;

    @Column(name="lot")
    private String lot;

    @Column(name="quantite")
    private Integer quantite;

    @Column(name="prix")
    private BigDecimal prix;

    @Column(name="date_fabrication")
    private Date dateFabrication;

    @Column(name="date_expiration")
    private Date dateExpiration;

    @Column(name="date_stock")
    private Date dateStock;

   
    @ManyToOne
    @JoinColumn(name = "Id_laboratoires ")
    private Laboratoire laboratoire;

    @ManyToOne
    @JoinColumn(name = "Id_medicament_fiches ")
    private MedicamentFiche medicamentFiche;


}
