package com.s5.pharmacie_backoffice.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MaladieSymptomeId implements Serializable{
    private Long idMaladie;
    private Long idSymptome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaladieSymptomeId that = (MaladieSymptomeId) o;
        return Objects.equals(idMaladie, that.idMaladie) &&
               Objects.equals(idSymptome, that.idSymptome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMaladie, idSymptome);
    }
}
