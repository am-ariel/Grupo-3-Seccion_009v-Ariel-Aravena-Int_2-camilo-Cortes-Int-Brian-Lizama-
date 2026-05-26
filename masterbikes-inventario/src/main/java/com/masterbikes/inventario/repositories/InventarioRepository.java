package com.masterbikes.inventario.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.masterbikes.inventario.models.InventarioModel;

@Repository
public interface InventarioRepository extends JpaRepository<InventarioModel, Long> {
    List<InventarioModel> findByAumento(Boolean aumento);
}
