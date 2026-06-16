package com.masterbikes.bicicletas.services;

import com.masterbikes.bicicletas.models.BicicletaModel;
import com.masterbikes.bicicletas.repositories.BicicletaRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BicicletaServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private BicicletaRepository bicicletaRepository;

    @InjectMocks
    private BicicletaService bicicletaService;

    @Test
    void obtenerTodasDebeListarBicicletas() {
        when(bicicletaRepository.findAll()).thenReturn(List.of(bicicletaValida(1L)));

        List<BicicletaModel> resultado = bicicletaService.obtenerTodas();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarBicicleta() {
        when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicletaValida(1L)));

        assertTrue(bicicletaService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(bicicletaRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(bicicletaService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void guardarBicicletaValidaDebePersistir() {
        BicicletaModel bicicleta = bicicletaValida(null);
        when(bicicletaRepository.save(bicicleta)).thenReturn(bicicletaValida(1L));

        BicicletaModel resultado = bicicletaService.guardar(bicicleta);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void bicicletaSinCodigoDebeSerInvalidaPorBeanValidation() {
        BicicletaModel bicicleta = bicicletaValida(null);
        bicicleta.setCodigo("");

        assertFalse(VALIDATOR.validate(bicicleta).isEmpty());
    }

    @Test
    void tarifaCeroDebeSerInvalidaPorReglaDeDominio() {
        BicicletaModel bicicleta = bicicletaValida(null);
        bicicleta.setTarifaHora(BigDecimal.ZERO);

        assertFalse(VALIDATOR.validate(bicicleta).isEmpty());
    }

    @Test
    void actualizarBicicletaExistenteDebeModificarEstado() {
        BicicletaModel existente = bicicletaValida(1L);
        BicicletaModel cambios = bicicletaValida(null);
        cambios.setEstado("EN_MANTENCION");
        when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(bicicletaRepository.save(existente)).thenReturn(existente);

        BicicletaModel resultado = bicicletaService.actualizar(1L, cambios);

        assertEquals("EN_MANTENCION", resultado.getEstado());
    }

    @Test
    void actualizarBicicletaInexistenteDebeLanzarNotFound() {
        when(bicicletaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> bicicletaService.actualizar(99L, bicicletaValida(null)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarBicicletaExistenteDebeEliminar() {
        when(bicicletaRepository.existsById(1L)).thenReturn(true);

        bicicletaService.eliminar(1L);

        verify(bicicletaRepository).deleteById(1L);
    }

    @Test
    void eliminarBicicletaInexistenteDebeLanzarNotFound() {
        when(bicicletaRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> bicicletaService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static BicicletaModel bicicletaValida(Long id) {
        BicicletaModel bicicleta = new BicicletaModel();
        bicicleta.setId(id);
        bicicleta.setCodigo("BIC-001");
        bicicleta.setTipo("Urbana");
        bicicleta.setEstado("DISPONIBLE");
        bicicleta.setTarifaHora(new BigDecimal("2500"));
        return bicicleta;
    }
}
