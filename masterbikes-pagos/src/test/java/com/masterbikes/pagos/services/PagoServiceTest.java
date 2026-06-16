package com.masterbikes.pagos.services;

import com.masterbikes.pagos.client.VentaClient;
import com.masterbikes.pagos.dtos.VentaResponse;
import com.masterbikes.pagos.models.PagoModel;
import com.masterbikes.pagos.repositories.PagoRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private VentaClient ventaClient;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void obtenerTodosDebeListarPagos() {
        when(pagoRepository.findAll()).thenReturn(List.of(pagoValido(1L, 100L)));

        List<PagoModel> resultado = pagoService.obtenerTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarPago() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pagoValido(1L, 100L)));

        assertTrue(pagoService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(pagoService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void guardarPagoValidoDebeValidarVentaYPersistir() {
        PagoModel pago = pagoValido(null, 100L);
        when(ventaClient.obtenerVentaPorId(100L)).thenReturn(ventaExistente(100L));
        when(pagoRepository.findByIdVenta(100L)).thenReturn(Optional.empty());
        when(pagoRepository.save(pago)).thenReturn(pagoValido(1L, 100L));

        PagoModel resultado = pagoService.guardar(pago);

        assertEquals(1L, resultado.getId());
        verify(ventaClient).obtenerVentaPorId(100L);
    }

    @Test
    void guardarPagoConVentaNoExistenteDebeRechazar() {
        PagoModel pago = pagoValido(null, 999L);
        when(ventaClient.obtenerVentaPorId(999L)).thenReturn(null);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pagoService.guardar(pago));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(pagoRepository, never()).save(pago);
    }

    @Test
    void guardarPagoDuplicadoDebeRechazar() {
        PagoModel pago = pagoValido(null, 100L);
        when(ventaClient.obtenerVentaPorId(100L)).thenReturn(ventaExistente(100L));
        when(pagoRepository.findByIdVenta(100L)).thenReturn(Optional.of(pagoValido(1L, 100L)));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pagoService.guardar(pago));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(pagoRepository, never()).save(pago);
    }

    @Test
    void pagoSinIdVentaDebeSerInvalidoPorBeanValidation() {
        PagoModel pago = pagoValido(null, null);

        assertFalse(VALIDATOR.validate(pago).isEmpty());
    }

    @Test
    void actualizarPagoExistenteDebeModificarEstado() {
        PagoModel existente = pagoValido(1L, 100L);
        PagoModel cambios = pagoValido(null, 101L);
        cambios.setEstado("REEMBOLSADO");
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ventaClient.obtenerVentaPorId(101L)).thenReturn(ventaExistente(101L));
        when(pagoRepository.findByIdVenta(101L)).thenReturn(Optional.empty());
        when(pagoRepository.save(existente)).thenReturn(existente);

        PagoModel resultado = pagoService.actualizar(1L, cambios);

        assertEquals("REEMBOLSADO", resultado.getEstado());
        assertEquals(101L, resultado.getIdVenta());
    }

    @Test
    void actualizarPagoInexistenteDebeLanzarNotFound() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> pagoService.actualizar(99L, pagoValido(null, 100L)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarPagoExistenteDebeEliminar() {
        when(pagoRepository.existsById(1L)).thenReturn(true);

        pagoService.eliminar(1L);

        verify(pagoRepository).deleteById(1L);
    }

    @Test
    void eliminarPagoInexistenteDebeLanzarNotFound() {
        when(pagoRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> pagoService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static PagoModel pagoValido(Long id, Long idVenta) {
        PagoModel pago = new PagoModel();
        pago.setId(id);
        pago.setIdVenta(idVenta);
        pago.setFechaPago(LocalDate.of(2026, 6, 15));
        pago.setMonto(new BigDecimal("25000"));
        pago.setMetodo("TARJETA");
        pago.setEstado("PAGADO");
        return pago;
    }

    private static VentaResponse ventaExistente(Long id) {
        VentaResponse venta = new VentaResponse();
        venta.setId(id);
        venta.setIdCliente(10L);
        venta.setFechaVenta(LocalDate.of(2026, 6, 15));
        venta.setTotal(new BigDecimal("25000"));
        venta.setEstado("CONFIRMADA");
        return venta;
    }
}
