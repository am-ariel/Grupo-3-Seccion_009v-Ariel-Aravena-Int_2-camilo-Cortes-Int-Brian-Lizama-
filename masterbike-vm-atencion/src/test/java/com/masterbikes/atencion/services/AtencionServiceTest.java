package com.masterbikes.atencion.services;

import com.masterbikes.atencion.models.AtencionModel;
import com.masterbikes.atencion.repositories.AtencionRepository;
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
class AtencionServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private AtencionRepository atencionRepository;

    @InjectMocks
    private AtencionService atencionService;

    @Test
    void obtenerTodosDebeListarAtenciones() {
        when(atencionRepository.findAll()).thenReturn(List.of(atencionValida(1L)));

        List<AtencionModel> resultado = atencionService.obtenerTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarAtencion() {
        when(atencionRepository.findById(1L)).thenReturn(Optional.of(atencionValida(1L)));

        Optional<AtencionModel> resultado = atencionService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(atencionRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(atencionService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void obtenerPorClienteDebeFiltrarPorIdCliente() {
        when(atencionRepository.findByIdCliente(10L)).thenReturn(List.of(atencionValida(1L)));

        List<AtencionModel> resultado = atencionService.obtenerPorCliente(10L);

        assertEquals(1, resultado.size());
        verify(atencionRepository).findByIdCliente(10L);
    }

    @Test
    void guardarAtencionValidaDebeNormalizarEstado() {
        AtencionModel atencion = atencionValida(null);
        atencion.setEstado(" pendiente ");
        when(atencionRepository.save(atencion)).thenReturn(atencion);

        AtencionModel resultado = atencionService.guardar(atencion);

        assertEquals("PENDIENTE", resultado.getEstado());
    }

    @Test
    void atencionSinIdClienteDebeSerInvalidaPorBeanValidation() {
        AtencionModel atencion = atencionValida(null);
        atencion.setIdCliente(null);

        assertFalse(VALIDATOR.validate(atencion).isEmpty());
    }

    @Test
    void atencionSinDescripcionDebeSerInvalidaPorBeanValidation() {
        AtencionModel atencion = atencionValida(null);
        atencion.setDescripcion("");

        assertFalse(VALIDATOR.validate(atencion).isEmpty());
    }

    @Test
    void actualizarAtencionExistenteDebeModificarYNormalizarEstado() {
        AtencionModel existente = atencionValida(1L);
        AtencionModel cambios = atencionValida(null);
        cambios.setEstado(" cerrada ");
        when(atencionRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(atencionRepository.save(existente)).thenReturn(existente);

        AtencionModel resultado = atencionService.actualizar(1L, cambios);

        assertEquals("CERRADA", resultado.getEstado());
    }

    @Test
    void actualizarAtencionInexistenteDebeLanzarNotFound() {
        when(atencionRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> atencionService.actualizar(99L, atencionValida(null)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarAtencionExistenteDebeEliminar() {
        when(atencionRepository.existsById(1L)).thenReturn(true);

        atencionService.eliminar(1L);

        verify(atencionRepository).deleteById(1L);
    }

    @Test
    void eliminarAtencionInexistenteDebeLanzarNotFound() {
        when(atencionRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> atencionService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static AtencionModel atencionValida(Long id) {
        AtencionModel atencion = new AtencionModel();
        atencion.setId(id);
        atencion.setIdCliente(10L);
        atencion.setIdBicicleta(20L);
        atencion.setFechaIngreso(LocalDate.now());
        atencion.setTipoServicio("Mantencion");
        atencion.setDescripcion("Ajuste general");
        atencion.setEstado("PENDIENTE");
        return atencion;
    }
}
