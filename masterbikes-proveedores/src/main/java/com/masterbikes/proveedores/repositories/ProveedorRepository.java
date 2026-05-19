package com.masterbikes.proveedores.repositories;

import com.masterbikes.proveedores.models.ProveedorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorModel, Long> {

    Optional<ProveedorModel> findByRut(String rut);
}
