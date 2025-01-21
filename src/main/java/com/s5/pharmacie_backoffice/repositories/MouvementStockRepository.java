package com.s5.pharmacie_backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.s5.pharmacie_backoffice.models.MouvementStock;
import com.s5.pharmacie_backoffice.models.*;

import java.util.List;


@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock,Long> {
    List<MouvementStock> findByTypeMouvement(TypeMouvement typeMouvement);

    List<MouvementStock> findByStock(Stock stock);
}
