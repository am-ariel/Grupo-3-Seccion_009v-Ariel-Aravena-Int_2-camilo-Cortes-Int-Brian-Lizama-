package com.masterbikes.bicicletas.services;

import com.masterbikes.bicicletas.models.BicicletaModel;
import com.masterbikes.bicicletas.repositories.BicicletaRepository;
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
public class BicicletaService {

    private static final Logger log = LoggerFactory.getLogger(BicicletaService.class);

    private final BicicletaRepository bicicletaRepository;

    public BicicletaService(BicicletaRepository bicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
    }

    public List<BicicletaModel> obtenerTodas() {
        return bicicletaRepository.findAll();
    }

    public Optional<BicicletaModel> obtenerPorId(Long id) {
        return bicicletaRepository.findById(id);
    }

    public BicicletaModel guardar(BicicletaModel bicicleta) {
        log.info("Guardando bicicleta con codigo {}", bicicleta.getCodigo());
        return bicicletaRepository.save(bicicleta);
    }

    public BicicletaModel actualizar(Long id, BicicletaModel bicicleta) {
        BicicletaModel existente = bicicletaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la bicicleta con id: " + id));

        existente.setCodigo(bicicleta.getCodigo());
        existente.setTipo(bicicleta.getTipo());
        existente.setEstado(bicicleta.getEstado());
        existente.setTarifaHora(bicicleta.getTarifaHora());

        log.info("Actualizando bicicleta con id {}", id);
        return bicicletaRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!bicicletaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la bicicleta con id: " + id);
        }
        log.info("Eliminando bicicleta con id {}", id);
        bicicletaRepository.deleteById(id);
    }
}
