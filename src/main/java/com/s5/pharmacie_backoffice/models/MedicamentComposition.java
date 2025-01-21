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
@Table(name = "medicaments_compositions")
public class MedicamentComposition {

    @EmbeddedId
    private MedicamentCompositionId id;

    @ManyToOne
    @MapsId("idComposition")
    @JoinColumn(name = "Id_compositions", referencedColumnName = "Id_compositions", nullable = false)
    private Composition composition;

    @ManyToOne
    @MapsId("idMedicamentFiche")
    @JoinColumn(name = "Id_medicament_fiches", referencedColumnName = "Id_medicament_fiches", nullable = false)
    private MedicamentFiche medicamentFiche;
    
    @Column(name="poid")
    private BigDecimal poid;


}
