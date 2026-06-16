package com.masterbikes.clientes.services;

import com.masterbikes.clientes.models.ClienteModel;
import com.masterbikes.clientes.repositories.ClienteRepository;
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
class ClienteServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void obtenerTodosDebeListarClientes() {
        ClienteModel cliente = clienteValido(1L, "11111111-1");
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteModel> resultado = clienteService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals("11111111-1", resultado.get(0).getRut());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarCliente() {
        ClienteModel cliente = clienteValido(1L, "11111111-1");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<ClienteModel> resultado = clienteService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ClienteModel> resultado = clienteService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void obtenerPorRutDebeConsultarRepository() {
        ClienteModel cliente = clienteValido(1L, "11111111-1");
        when(clienteRepository.findByRut("11111111-1")).thenReturn(Optional.of(cliente));

        Optional<ClienteModel> resultado = clienteService.obtenerPorRut("11111111-1");

        assertTrue(resultado.isPresent());
        assertEquals("11111111-1", resultado.get().getRut());
    }

    @Test
    void guardarClienteValidoDebePersistir() {
        ClienteModel cliente = clienteValido(null, "11111111-1");
        when(clienteRepository.findByRut("11111111-1")).thenReturn(Optional.empty());
        when(clienteRepository.save(cliente)).thenReturn(clienteValido(1L, "11111111-1"));

        ClienteModel resultado = clienteService.guardar(cliente);

        assertEquals(1L, resultado.getId());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void guardarClienteConRutDuplicadoDebeRechazar() {
        ClienteModel cliente = clienteValido(null, "11111111-1");
        when(clienteRepository.findByRut("11111111-1")).thenReturn(Optional.of(clienteValido(1L, "11111111-1")));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> clienteService.guardar(cliente));

        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(clienteRepository, never()).save(cliente);
    }

    @Test
    void clienteSinRutDebeSerInvalidoPorBeanValidation() {
        ClienteModel cliente = clienteValido(null, null);

        assertFalse(VALIDATOR.validate(cliente).isEmpty());
    }

    @Test
    void actualizarClienteExistenteDebeModificarDatos() {
        ClienteModel existente = clienteValido(1L, "11111111-1");
        ClienteModel cambios = clienteValido(null, "22222222-2");
        cambios.setNombre("Cliente Actualizado");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(clienteRepository.findByRut("22222222-2")).thenReturn(Optional.empty());
        when(clienteRepository.save(existente)).thenReturn(existente);

        ClienteModel resultado = clienteService.actualizar(1L, cambios);

        assertEquals("22222222-2", resultado.getRut());
        assertEquals("Cliente Actualizado", resultado.getNombre());
    }

    @Test
    void actualizarClienteInexistenteDebeLanzarNotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> clienteService.actualizar(99L, clienteValido(null, "11111111-1")));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarClienteExistenteDebeEliminar() {
        when(clienteRepository.existsById(1L)).thenReturn(true);

        clienteService.eliminar(1L);

        verify(clienteRepository).deleteById(1L);
    }

    @Test
    void eliminarClienteInexistenteDebeLanzarNotFound() {
        when(clienteRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> clienteService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static ClienteModel clienteValido(Long id, String rut) {
        ClienteModel cliente = new ClienteModel();
        cliente.setId(id);
        cliente.setRut(rut);
        cliente.setNombre("Cliente Demo");
        cliente.setCorreo("cliente@masterbikes.cl");
        cliente.setTelefono("+56911111111");
        cliente.setDireccion("Av. Principal 123");
        return cliente;
    }
}
