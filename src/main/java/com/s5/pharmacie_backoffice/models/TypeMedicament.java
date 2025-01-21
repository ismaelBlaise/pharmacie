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
@Table(name="types_medicament")
public class TypeMedicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_types_medicament")
    private Long idTypeMedicament; 

    @Column(name="type_medicament")
    private String typeMedicament;
    
}
