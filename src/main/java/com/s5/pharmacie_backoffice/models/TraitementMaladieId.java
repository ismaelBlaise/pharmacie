package com.s5.pharmacie_backoffice.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TraitementMaladieId implements Serializable{
    private Long idMaladie;
    private Long idMedicamentFiche;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraitementMaladieId that = (TraitementMaladieId) o;
        return Objects.equals(idMaladie, that.idMaladie) &&
               Objects.equals(idMedicamentFiche, that.idMedicamentFiche);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMaladie, idMedicamentFiche);
    }
}
