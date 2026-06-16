package com.masterbikes.ventas.services;

import com.masterbikes.ventas.models.VentaModel;
import com.masterbikes.ventas.repositories.VentaRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VentaServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @Test
    void obtenerTodasDebeListarVentas() {
        when(ventaRepository.findAll()).thenReturn(List.of(ventaValida(1L)));

        List<VentaModel> resultado = ventaService.obtenerTodas();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarVenta() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(ventaValida(1L)));

        assertTrue(ventaService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(ventaService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void guardarVentaValidaDebePersistir() {
        VentaModel venta = ventaValida(null);
        when(ventaRepository.save(venta)).thenReturn(ventaValida(1L));

        VentaModel resultado = ventaService.guardar(venta);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void ventaSinIdClienteDebeSerInvalidaPorBeanValidation() {
        VentaModel venta = ventaValida(null);
        venta.setIdCliente(null);

        assertFalse(VALIDATOR.validate(venta).isEmpty());
    }

    @Test
    void ventaConTotalCeroDebeSerInvalidaPorReglaDeDominio() {
        VentaModel venta = ventaValida(null);
        venta.setTotal(BigDecimal.ZERO);

        assertFalse(VALIDATOR.validate(venta).isEmpty());
    }

    @Test
    void actualizarVentaExistenteDebeModificarEstado() {
        VentaModel existente = ventaValida(1L);
        VentaModel cambios = ventaValida(null);
        cambios.setEstado("ANULADA");
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(ventaRepository.save(existente)).thenReturn(existente);

        VentaModel resultado = ventaService.actualizar(1L, cambios);

        assertEquals("ANULADA", resultado.getEstado());
    }

    @Test
    void actualizarVentaInexistenteDebeLanzarNotFound() {
        when(ventaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> ventaService.actualizar(99L, ventaValida(null)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarVentaExistenteDebeEliminar() {
        when(ventaRepository.existsById(1L)).thenReturn(true);

        ventaService.eliminar(1L);

        verify(ventaRepository).deleteById(1L);
    }

    @Test
    void eliminarVentaInexistenteDebeLanzarNotFound() {
        when(ventaRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> ventaService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static VentaModel ventaValida(Long id) {
        VentaModel venta = new VentaModel();
        venta.setId(id);
        venta.setIdCliente(10L);
        venta.setFechaVenta(LocalDate.of(2026, 6, 15));
        venta.setTotal(new BigDecimal("25000"));
        venta.setEstado("CONFIRMADA");
        return venta;
    }
}
