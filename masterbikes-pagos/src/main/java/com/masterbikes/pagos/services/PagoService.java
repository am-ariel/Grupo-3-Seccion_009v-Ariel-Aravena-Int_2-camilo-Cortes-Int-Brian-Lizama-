package com.masterbikes.pagos.services;

import com.masterbikes.pagos.client.VentaClient;
import com.masterbikes.pagos.dtos.VentaResponse;
import com.masterbikes.pagos.models.PagoModel;
import com.masterbikes.pagos.repositories.PagoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoService.class);

    private final PagoRepository pagoRepository;
    private final VentaClient ventaClient;

    public PagoService(PagoRepository pagoRepository, VentaClient ventaClient) {
        this.pagoRepository = pagoRepository;
        this.ventaClient = ventaClient;
    }

    public List<PagoModel> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Optional<PagoModel> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public PagoModel guardar(PagoModel pago) {
        // Regla de negocio: antes de pagar se valida que la venta exista en el otro microservicio.
        validarVentaExistente(pago.getIdVenta());
        validarVentaSinPagoPrevio(pago.getIdVenta(), null);
        log.info("Guardando pago para venta {}", pago.getIdVenta());
        return pagoRepository.save(pago);
    }

    public PagoModel actualizar(Long id, PagoModel pago) {
        PagoModel existente = pagoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el pago con id: " + id));

        validarVentaExistente(pago.getIdVenta());
        validarVentaSinPagoPrevio(pago.getIdVenta(), id);

        existente.setIdVenta(pago.getIdVenta());
        existente.setFechaPago(pago.getFechaPago());
        existente.setMonto(pago.getMonto());
        existente.setMetodo(pago.getMetodo());
        existente.setEstado(pago.getEstado());

        log.info("Actualizando pago con id {}", id);
        return pagoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el pago con id: " + id);
        }
        log.info("Eliminando pago con id {}", id);
        pagoRepository.deleteById(id);
    }

    private void validarVentaExistente(Long idVenta) {
        try {
            log.info("Consultando microservicio de ventas para validar venta {}", idVenta);
            VentaResponse venta = ventaClient.obtenerVentaPorId(idVenta);
            if (venta == null || venta.getId() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La venta no existe con id: " + idVenta);
            }
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La venta no existe con id: " + idVenta);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No se pudo validar la venta contra el microservicio de ventas");
        }
    }

    private void validarVentaSinPagoPrevio(Long idVenta, Long idPagoActual) {
        Optional<PagoModel> pagoExistente = pagoRepository.findByIdVenta(idVenta);
        if (pagoExistente.isPresent() && !Objects.equals(pagoExistente.get().getId(), idPagoActual)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La venta con id " + idVenta + " ya tiene un pago asociado"
            );
        }
    }
}
