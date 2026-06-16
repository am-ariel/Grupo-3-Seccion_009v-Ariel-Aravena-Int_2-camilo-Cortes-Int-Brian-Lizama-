package com.masterbikes.atencion.repositories;

import com.masterbikes.atencion.models.AtencionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtencionRepository extends JpaRepository<AtencionModel, Long> {

    List<AtencionModel> findByIdCliente(Long idCliente);
}
