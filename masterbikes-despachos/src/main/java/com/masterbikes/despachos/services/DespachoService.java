package com.masterbikes.despachos.services;

import com.masterbikes.despachos.models.DespachoModel;
import com.masterbikes.despachos.repositories.DespachoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DespachoService {

    private static final Logger log = LoggerFactory.getLogger(DespachoService.class);

    private final DespachoRepository despachoRepository;

    public DespachoService(DespachoRepository despachoRepository) {
        this.despachoRepository = despachoRepository;
    }

    public List<DespachoModel> obtenerTodos() {
        return despachoRepository.findAll();
    }

    public Optional<DespachoModel> obtenerPorId(Long id) {
        return despachoRepository.findById(id);
    }

    public DespachoModel guardar(DespachoModel despacho) {
        log.info("Guardando despacho para venta {}", despacho.getIdVenta());
        return despachoRepository.save(despacho);
    }

    public DespachoModel actualizar(Long id, DespachoModel despacho) {
        DespachoModel existente = despachoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el despacho con id: " + id));

        existente.setIdVenta(despacho.getIdVenta());
        existente.setDireccion(despacho.getDireccion());
        existente.setEstado(despacho.getEstado());
        existente.setFechaEstimada(despacho.getFechaEstimada());

        log.info("Actualizando despacho con id {}", id);
        return despachoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!despachoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el despacho con id: " + id);
        }
        log.info("Eliminando despacho con id {}", id);
        despachoRepository.deleteById(id);
    }
}
