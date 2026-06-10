package com.masterbikes.arriendos.services;

import com.masterbikes.arriendos.models.ArriendoModel;
import com.masterbikes.arriendos.repositories.ArriendoRepository;
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
public class ArriendoService {

    private static final Logger log = LoggerFactory.getLogger(ArriendoService.class);

    private final ArriendoRepository arriendoRepository;

    public ArriendoService(ArriendoRepository arriendoRepository) {
        this.arriendoRepository = arriendoRepository;
    }

    public List<ArriendoModel> obtenerTodos() {
        return arriendoRepository.findAll();
    }

    public Optional<ArriendoModel> obtenerPorId(Long id) {
        return arriendoRepository.findById(id);
    }

    public ArriendoModel guardar(ArriendoModel arriendo) {
        validarFechas(arriendo);
        log.info("Guardando arriendo para cliente {} y bicicleta {}", arriendo.getIdCliente(), arriendo.getIdBicicleta());
        return arriendoRepository.save(arriendo);
    }

    public ArriendoModel actualizar(Long id, ArriendoModel arriendo) {
        ArriendoModel existente = arriendoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el arriendo con id: " + id));

        validarFechas(arriendo);
        existente.setIdCliente(arriendo.getIdCliente());
        existente.setIdBicicleta(arriendo.getIdBicicleta());
        existente.setFechaInicio(arriendo.getFechaInicio());
        existente.setFechaFin(arriendo.getFechaFin());
        existente.setCosto(arriendo.getCosto());

        log.info("Actualizando arriendo con id {}", id);
        return arriendoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!arriendoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el arriendo con id: " + id);
        }
        log.info("Eliminando arriendo con id {}", id);
        arriendoRepository.deleteById(id);
    }

    private void validarFechas(ArriendoModel arriendo) {
        // Regla de negocio: un arriendo no puede terminar antes de comenzar.
        if (arriendo.getFechaInicio() != null && arriendo.getFechaFin() != null
                && arriendo.getFechaFin().isBefore(arriendo.getFechaInicio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }
}
