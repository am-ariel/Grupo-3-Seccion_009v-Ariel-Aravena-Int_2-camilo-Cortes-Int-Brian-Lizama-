package com.masterbikes.pagos.services;

import com.masterbikes.pagos.client.VentaClient;
import com.masterbikes.pagos.dtos.VentaResponse;
import com.masterbikes.pagos.models.PagoModel;
import com.masterbikes.pagos.repositories.PagoRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PagoService {

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
        validarVentaExistente(pago.getIdVenta());
        validarVentaSinPagoPrevio(pago.getIdVenta());
        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    private void validarVentaExistente(Long idVenta) {
        try {
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

    private void validarVentaSinPagoPrevio(Long idVenta) {
        if (pagoRepository.existsByIdVenta(idVenta)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "La venta con id " + idVenta + " ya tiene un pago asociado"
            );
        }
    }
}
