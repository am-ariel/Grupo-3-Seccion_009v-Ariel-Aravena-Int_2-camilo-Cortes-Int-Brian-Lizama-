package com.masterbikes.arriendos.services;

import com.masterbikes.arriendos.models.ArriendoModel;
import com.masterbikes.arriendos.repositories.ArriendoRepository;
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
class ArriendoServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ArriendoRepository arriendoRepository;

    @InjectMocks
    private ArriendoService arriendoService;

    @Test
    void obtenerTodosDebeListarArriendos() {
        when(arriendoRepository.findAll()).thenReturn(List.of(arriendoValido(1L)));

        List<ArriendoModel> resultado = arriendoService.obtenerTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarArriendo() {
        when(arriendoRepository.findById(1L)).thenReturn(Optional.of(arriendoValido(1L)));

        assertTrue(arriendoService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(arriendoRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(arriendoService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void guardarArriendoValidoDebePersistir() {
        ArriendoModel arriendo = arriendoValido(null);
        when(arriendoRepository.save(arriendo)).thenReturn(arriendoValido(1L));

        ArriendoModel resultado = arriendoService.guardar(arriendo);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void guardarArriendoConFechaFinAnteriorDebeRechazar() {
        ArriendoModel arriendo = arriendoValido(null);
        arriendo.setFechaInicio(LocalDate.of(2026, 6, 10));
        arriendo.setFechaFin(LocalDate.of(2026, 6, 9));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> arriendoService.guardar(arriendo));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(arriendoRepository, never()).save(arriendo);
    }

    @Test
    void arriendoSinIdClienteDebeSerInvalidoPorBeanValidation() {
        ArriendoModel arriendo = arriendoValido(null);
        arriendo.setIdCliente(null);

        assertFalse(VALIDATOR.validate(arriendo).isEmpty());
    }

    @Test
    void actualizarArriendoExistenteDebeModificarCosto() {
        ArriendoModel existente = arriendoValido(1L);
        ArriendoModel cambios = arriendoValido(null);
        cambios.setCosto(new BigDecimal("15000"));
        when(arriendoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(arriendoRepository.save(existente)).thenReturn(existente);

        ArriendoModel resultado = arriendoService.actualizar(1L, cambios);

        assertEquals(new BigDecimal("15000"), resultado.getCosto());
    }

    @Test
    void actualizarArriendoInexistenteDebeLanzarNotFound() {
        when(arriendoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> arriendoService.actualizar(99L, arriendoValido(null)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarArriendoExistenteDebeEliminar() {
        when(arriendoRepository.existsById(1L)).thenReturn(true);

        arriendoService.eliminar(1L);

        verify(arriendoRepository).deleteById(1L);
    }

    @Test
    void eliminarArriendoInexistenteDebeLanzarNotFound() {
        when(arriendoRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> arriendoService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static ArriendoModel arriendoValido(Long id) {
        ArriendoModel arriendo = new ArriendoModel();
        arriendo.setId(id);
        arriendo.setIdCliente(10L);
        arriendo.setIdBicicleta(20L);
        arriendo.setFechaInicio(LocalDate.of(2026, 6, 10));
        arriendo.setFechaFin(LocalDate.of(2026, 6, 12));
        arriendo.setCosto(new BigDecimal("10000"));
        return arriendo;
    }
}
