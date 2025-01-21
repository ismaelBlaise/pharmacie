package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.MedicamentComposition;
import com.s5.pharmacie_backoffice.models.MedicamentCompositionId;
import com.s5.pharmacie_backoffice.models.MedicamentFiche;

@Repository
public interface MedicamentCompositionRepository extends JpaRepository<MedicamentComposition,MedicamentCompositionId>{
    Page<MedicamentComposition> findByMedicamentFiche(Pageable pageable,MedicamentFiche medicamentFiche);
}
