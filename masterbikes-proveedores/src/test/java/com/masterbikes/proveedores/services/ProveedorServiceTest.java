package com.masterbikes.proveedores.services;

import com.masterbikes.proveedores.models.ProveedorModel;
import com.masterbikes.proveedores.repositories.ProveedorRepository;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProveedorServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    @Test
    void obtenerTodosDebeListarProveedores() {
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedorValido(1L, "76123456-7")));

        List<ProveedorModel> resultado = proveedorService.obtenerTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarProveedor() {
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(proveedorValido(1L, "76123456-7")));

        assertTrue(proveedorService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(proveedorService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void obtenerPorRutDebeConsultarRepository() {
        when(proveedorRepository.findByRut("76123456-7")).thenReturn(Optional.of(proveedorValido(1L, "76123456-7")));

        Optional<ProveedorModel> resultado = proveedorService.obtenerPorRut("76123456-7");

        assertTrue(resultado.isPresent());
    }

    @Test
    void guardarProveedorValidoDebePersistir() {
        ProveedorModel proveedor = proveedorValido(null, "76123456-7");
        when(proveedorRepository.findByRut("76123456-7")).thenReturn(Optional.empty());
        when(proveedorRepository.save(proveedor)).thenReturn(proveedorValido(1L, "76123456-7"));

        ProveedorModel resultado = proveedorService.guardar(proveedor);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void guardarProveedorConRutDuplicadoDebeRechazar() {
        ProveedorModel proveedor = proveedorValido(null, "76123456-7");
        when(proveedorRepository.findByRut("76123456-7")).thenReturn(Optional.of(proveedorValido(1L, "76123456-7")));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> proveedorService.guardar(proveedor));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(proveedorRepository, never()).save(proveedor);
    }

    @Test
    void proveedorSinRutDebeSerInvalidoPorBeanValidation() {
        ProveedorModel proveedor = proveedorValido(null, null);

        assertFalse(VALIDATOR.validate(proveedor).isEmpty());
    }

    @Test
    void actualizarProveedorExistenteDebeModificarDatos() {
        ProveedorModel existente = proveedorValido(1L, "76123456-7");
        ProveedorModel cambios = proveedorValido(null, "76999999-9");
        cambios.setNombre("Proveedor Actualizado");
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(proveedorRepository.findByRut("76999999-9")).thenReturn(Optional.empty());
        when(proveedorRepository.save(existente)).thenReturn(existente);

        ProveedorModel resultado = proveedorService.actualizar(1L, cambios);

        assertEquals("Proveedor Actualizado", resultado.getNombre());
        assertEquals("76999999-9", resultado.getRut());
    }

    @Test
    void actualizarProveedorInexistenteDebeLanzarNotFound() {
        when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> proveedorService.actualizar(99L, proveedorValido(null, "76123456-7")));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarProveedorExistenteDebeEliminar() {
        when(proveedorRepository.existsById(1L)).thenReturn(true);

        proveedorService.eliminar(1L);

        verify(proveedorRepository).deleteById(1L);
    }

    @Test
    void eliminarProveedorInexistenteDebeLanzarNotFound() {
        when(proveedorRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> proveedorService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static ProveedorModel proveedorValido(Long id, String rut) {
        ProveedorModel proveedor = new ProveedorModel();
        proveedor.setId(id);
        proveedor.setRut(rut);
        proveedor.setNombre("Proveedor Demo");
        proveedor.setCorreo("proveedor@masterbikes.cl");
        proveedor.setTelefono("+56922222222");
        proveedor.setDireccion("Bodega Central 456");
        return proveedor;
    }
}
