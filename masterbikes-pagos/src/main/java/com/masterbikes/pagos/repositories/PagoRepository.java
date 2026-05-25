package com.masterbikes.pagos.repositories;

import com.masterbikes.pagos.models.PagoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<PagoModel, Long> {
    boolean existsByIdVenta(Long idVenta);
}
