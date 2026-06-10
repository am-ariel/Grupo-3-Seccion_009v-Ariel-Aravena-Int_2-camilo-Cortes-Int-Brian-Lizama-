package com.masterbikes.inventario.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.masterbikes.inventario.models.InventarioModel;
import com.masterbikes.inventario.repositories.InventarioRepository;

@Service
@Transactional
public class InventarioService {

    private static final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<InventarioModel> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    public Optional<InventarioModel> obtenerPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public InventarioModel guardar(InventarioModel inventario) {
        marcarAumentoSegunStock(inventario);
        log.info("Guardando producto de inventario {}", inventario.getNombreProducto());
        return inventarioRepository.save(inventario);
    }

    public InventarioModel actualizar(Long id, InventarioModel inventario) {
        InventarioModel existente = inventarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto de inventario con id: " + id));

        existente.setNombreProducto(inventario.getNombreProducto());
        existente.setCategoria(inventario.getCategoria());
        existente.setStock(inventario.getStock());
        existente.setStockMinimo(inventario.getStockMinimo());
        existente.setAumento(inventario.getAumento());
        marcarAumentoSegunStock(existente);

        log.info("Actualizando producto de inventario con id {}", id);
        return inventarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el producto de inventario con id: " + id);
        }
        log.info("Eliminando producto de inventario con id {}", id);
        inventarioRepository.deleteById(id);
    }

    public List<InventarioModel> obtenerPorAumento(Boolean aumento)
    {return inventarioRepository.findByAumento(aumento);
    }

    private void marcarAumentoSegunStock(InventarioModel inventario) {
        // Regla de negocio: si el stock llega al minimo, queda marcado para aumento.
        if (inventario.getStock() != null && inventario.getStockMinimo() != null) {
            inventario.setAumento(inventario.getStock() <= inventario.getStockMinimo());
        }
    }
}
