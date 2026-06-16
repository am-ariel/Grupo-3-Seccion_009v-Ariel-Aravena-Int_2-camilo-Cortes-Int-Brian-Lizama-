package com.masterbikes.atencion.services;

import com.masterbikes.atencion.models.AtencionModel;
import com.masterbikes.atencion.repositories.AtencionRepository;
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
public class AtencionService {

    private static final Logger log = LoggerFactory.getLogger(AtencionService.class);

    private final AtencionRepository atencionRepository;

    public AtencionService(AtencionRepository atencionRepository) {
        this.atencionRepository = atencionRepository;
    }

    public List<AtencionModel> obtenerTodos() {
        return atencionRepository.findAll();
    }

    public Optional<AtencionModel> obtenerPorId(Long id) {
        return atencionRepository.findById(id);
    }

    public List<AtencionModel> obtenerPorCliente(Long idCliente) {
        return atencionRepository.findByIdCliente(idCliente);
    }

    public AtencionModel guardar(AtencionModel atencion) {
        normalizarEstado(atencion);
        log.info("Guardando atencion para cliente {} y bicicleta {}", atencion.getIdCliente(), atencion.getIdBicicleta());
        return atencionRepository.save(atencion);
    }

    public AtencionModel actualizar(Long id, AtencionModel atencion) {
        AtencionModel existente = atencionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la atencion con id: " + id));

        existente.setIdCliente(atencion.getIdCliente());
        existente.setIdBicicleta(atencion.getIdBicicleta());
        existente.setFechaIngreso(atencion.getFechaIngreso());
        existente.setTipoServicio(atencion.getTipoServicio());
        existente.setDescripcion(atencion.getDescripcion());
        existente.setEstado(atencion.getEstado());
        normalizarEstado(existente);

        log.info("Actualizando atencion con id {}", id);
        return atencionRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!atencionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la atencion con id: " + id);
        }
        log.info("Eliminando atencion con id {}", id);
        atencionRepository.deleteById(id);
    }

    private void normalizarEstado(AtencionModel atencion) {
        if (atencion.getEstado() != null) {
            atencion.setEstado(atencion.getEstado().trim().toUpperCase());
        }
    }
}
