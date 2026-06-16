package com.masterbikes.inventario.services;

import com.masterbikes.inventario.models.InventarioModel;
import com.masterbikes.inventario.repositories.InventarioRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void obtenerTodosDebeListarInventario() {
        when(inventarioRepository.findAll()).thenReturn(List.of(inventarioValido(1L)));

        List<InventarioModel> resultado = inventarioService.obtenerTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarProducto() {
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inventarioValido(1L)));

        assertTrue(inventarioService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(inventarioService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void guardarConStockMenorOIgualAlMinimoDebeMarcarAumento() {
        InventarioModel inventario = inventarioValido(null);
        inventario.setStock(2);
        inventario.setStockMinimo(5);
        when(inventarioRepository.save(inventario)).thenReturn(inventario);

        InventarioModel resultado = inventarioService.guardar(inventario);

        assertTrue(resultado.getAumento());
    }

    @Test
    void guardarConStockSobreMinimoDebeQuitarAumento() {
        InventarioModel inventario = inventarioValido(null);
        inventario.setStock(10);
        inventario.setStockMinimo(5);
        when(inventarioRepository.save(inventario)).thenReturn(inventario);

        InventarioModel resultado = inventarioService.guardar(inventario);

        assertFalse(resultado.getAumento());
    }

    @Test
    void inventarioSinNombreDebeSerInvalidoPorBeanValidation() {
        InventarioModel inventario = inventarioValido(null);
        inventario.setNombreProducto("");

        assertFalse(VALIDATOR.validate(inventario).isEmpty());
    }

    @Test
    void obtenerPorAumentoDebeConsultarRepository() {
        when(inventarioRepository.findByAumento(true)).thenReturn(List.of(inventarioValido(1L)));

        List<InventarioModel> resultado = inventarioService.obtenerPorAumento(true);

        assertEquals(1, resultado.size());
        verify(inventarioRepository).findByAumento(true);
    }

    @Test
    void actualizarInventarioExistenteDebeRecalcularAumento() {
        InventarioModel existente = inventarioValido(1L);
        InventarioModel cambios = inventarioValido(null);
        cambios.setStock(3);
        cambios.setStockMinimo(3);
        cambios.setAumento(false);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(inventarioRepository.save(existente)).thenReturn(existente);

        InventarioModel resultado = inventarioService.actualizar(1L, cambios);

        assertTrue(resultado.getAumento());
    }

    @Test
    void actualizarInventarioInexistenteDebeLanzarNotFound() {
        when(inventarioRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> inventarioService.actualizar(99L, inventarioValido(null)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarInventarioExistenteDebeEliminar() {
        when(inventarioRepository.existsById(1L)).thenReturn(true);

        inventarioService.eliminar(1L);

        verify(inventarioRepository).deleteById(1L);
    }

    @Test
    void eliminarInventarioInexistenteDebeLanzarNotFound() {
        when(inventarioRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> inventarioService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static InventarioModel inventarioValido(Long id) {
        InventarioModel inventario = new InventarioModel();
        inventario.setId(id);
        inventario.setNombreProducto("Cadena");
        inventario.setCategoria("Repuesto");
        inventario.setStock(10);
        inventario.setStockMinimo(5);
        inventario.setAumento(false);
        return inventario;
    }
}
