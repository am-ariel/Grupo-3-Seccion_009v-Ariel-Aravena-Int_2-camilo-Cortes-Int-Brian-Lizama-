package com.masterbikes.ventas.services;

import com.masterbikes.ventas.models.VentaModel;
import com.masterbikes.ventas.repositories.VentaRepository;
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
public class VentaService {

    private static final Logger log = LoggerFactory.getLogger(VentaService.class);

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    public List<VentaModel> obtenerTodas() {
        return ventaRepository.findAll();
    }

    public Optional<VentaModel> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public VentaModel guardar(VentaModel venta) {
        log.info("Guardando venta para cliente {}", venta.getIdCliente());
        return ventaRepository.save(venta);
    }

    public VentaModel actualizar(Long id, VentaModel venta) {
        VentaModel existente = ventaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la venta con id: " + id));

        existente.setIdCliente(venta.getIdCliente());
        existente.setFechaVenta(venta.getFechaVenta());
        existente.setTotal(venta.getTotal());
        existente.setEstado(venta.getEstado());

        log.info("Actualizando venta con id {}", id);
        return ventaRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la venta con id: " + id);
        }
        log.info("Eliminando venta con id {}", id);
        ventaRepository.deleteById(id);
    }
}
