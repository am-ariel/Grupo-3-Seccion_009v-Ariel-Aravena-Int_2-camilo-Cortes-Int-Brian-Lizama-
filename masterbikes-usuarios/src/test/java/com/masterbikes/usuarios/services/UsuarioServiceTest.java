package com.masterbikes.usuarios.services;

import com.masterbikes.usuarios.models.UsuarioModel;
import com.masterbikes.usuarios.repositories.UsuarioRepository;
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
class UsuarioServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void obtenerTodosDebeListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioValido(1L)));

        List<UsuarioModel> resultado = usuarioService.obtenerTodos();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorIdExistenteDebeRetornarUsuario() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioValido(1L)));

        assertTrue(usuarioService.obtenerPorId(1L).isPresent());
    }

    @Test
    void obtenerPorIdInexistenteDebeRetornarVacio() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertTrue(usuarioService.obtenerPorId(99L).isEmpty());
    }

    @Test
    void guardarUsuarioValidoDebePersistir() {
        UsuarioModel usuario = usuarioValido(null);
        when(usuarioRepository.save(usuario)).thenReturn(usuarioValido(1L));

        UsuarioModel resultado = usuarioService.guardar(usuario);

        assertEquals(1L, resultado.getId());
    }

    @Test
    void usuarioSinCorreoDebeSerInvalidoPorBeanValidation() {
        UsuarioModel usuario = usuarioValido(null);
        usuario.setCorreo("");

        assertFalse(VALIDATOR.validate(usuario).isEmpty());
    }

    @Test
    void actualizarUsuarioExistenteDebeModificarDatos() {
        UsuarioModel existente = usuarioValido(1L);
        UsuarioModel cambios = usuarioValido(null);
        cambios.setRol("ADMIN");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(existente)).thenReturn(existente);

        UsuarioModel resultado = usuarioService.actualizar(1L, cambios);

        assertEquals("ADMIN", resultado.getRol());
    }

    @Test
    void actualizarUsuarioInexistenteDebeLanzarNotFound() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> usuarioService.actualizar(99L, usuarioValido(null)));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void eliminarUsuarioExistenteDebeEliminar() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        usuarioService.eliminar(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void eliminarUsuarioInexistenteDebeLanzarNotFound() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> usuarioService.eliminar(99L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    private static UsuarioModel usuarioValido(Long id) {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(id);
        usuario.setNombre("Usuario Demo");
        usuario.setCorreo("usuario@masterbikes.cl");
        usuario.setRol("OPERADOR");
        return usuario;
    }
}
