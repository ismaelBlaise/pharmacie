package com.s5.pharmacie_backoffice.models;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prix_medicament")
public class PrixMedicament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prix_medicament")
    private Long id;

    @Column(name = "montant")
    private BigDecimal montant;

    @ManyToOne
    @JoinColumn(name = "Id_medicament_fiches", nullable = false)
    private MedicamentFiche medicamentFiche;

    @Column(name = "date_chamgement")
    private Date dateChangement;
}
