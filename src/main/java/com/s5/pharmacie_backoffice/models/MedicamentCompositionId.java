package com.s5.pharmacie_backoffice.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MedicamentCompositionId implements Serializable{
    private Long idComposition;
    private Long idMedicamentFiche;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicamentCompositionId that = (MedicamentCompositionId) o;
        return Objects.equals(idComposition, that.idComposition) &&
               Objects.equals(idMedicamentFiche, that.idMedicamentFiche);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idComposition, idMedicamentFiche);
    }
}
