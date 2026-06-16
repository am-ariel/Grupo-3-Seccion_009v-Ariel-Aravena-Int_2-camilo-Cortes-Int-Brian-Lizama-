package com.masterbikes.despachos.services;

import com.masterbikes.despachos.models.DespachoModel;
import com.masterbikes.despachos.repositories.DespachoRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
class DespachoServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private DespachoRepository despachoRepository;

    @InjectMocks
    private DespachoService despachoService;

    @Test
    void obtenerTodosDebeListarDespachos() {
        when(despachoRepository.findAll()).thenReturn(List.of(despachoValido(1L)));

        List<DespachoModel> resultado = despachoService.obtenerTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarDespacho() {
        when(despachoRepository.findById(1L)).thenReturn(Optional.of(despachoValido(1L)));

        assertTrue(despachoService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(despachoRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(despachoService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void guardarDespachoValidoDebePersistir() {
        DespachoModel despacho = despachoValido(null);
        when(despachoRepository.save(despacho)).thenReturn(despachoValido(1L));

        DespachoModel resultado = despachoService.guardar(despacho);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void despachoSinIdVentaDebeSerInvalidoPorBeanValidation() {
        DespachoModel despacho = despachoValido(null);
        despacho.setIdVenta(null);

        assertFalse(VALIDATOR.validate(despacho).isEmpty());
    }

    @Test
    void despachoSinDireccionDebeSerInvalidoPorReglaDeDominio() {
        DespachoModel despacho = despachoValido(null);
        despacho.setDireccion("");

        assertFalse(VALIDATOR.validate(despacho).isEmpty());
    }

    @Test
    void actualizarDespachoExistenteDebeModificarEstado() {
        DespachoModel existente = despachoValido(1L);
        DespachoModel cambios = despachoValido(null);
        cambios.setEstado("ENTREGADO");
        when(despachoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(despachoRepository.save(existente)).thenReturn(existente);

        DespachoModel resultado = despachoService.actualizar(1L, cambios);

        assertEquals("ENTREGADO", resultado.getEstado());
    }

    @Test
    void actualizarDespachoInexistenteDebeLanzarNotFound() {
        when(despachoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> despachoService.actualizar(99L, despachoValido(null)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarDespachoExistenteDebeEliminar() {
        when(despachoRepository.existsById(1L)).thenReturn(true);

        despachoService.eliminar(1L);

        verify(despachoRepository).deleteById(1L);
    }

    @Test
    void eliminarDespachoInexistenteDebeLanzarNotFound() {
        when(despachoRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> despachoService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static DespachoModel despachoValido(Long id) {
        DespachoModel despacho = new DespachoModel();
        despacho.setId(id);
        despacho.setIdVenta(100L);
        despacho.setDireccion("Av. Entrega 123");
        despacho.setEstado("PREPARACION");
        despacho.setFechaEstimada(LocalDate.of(2026, 6, 20));
        return despacho;
    }
}
