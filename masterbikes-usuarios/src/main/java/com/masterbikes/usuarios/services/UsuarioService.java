package com.masterbikes.usuarios.services;

import com.masterbikes.usuarios.models.UsuarioModel;
import com.masterbikes.usuarios.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioModel> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioModel> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModel guardar(UsuarioModel usuario) {
        log.info("Guardando usuario con correo {}", usuario.getCorreo());
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel actualizar(Long id, UsuarioModel usuario) {
        UsuarioModel existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id: " + id));

        existente.setNombre(usuario.getNombre());
        existente.setCorreo(usuario.getCorreo());
        existente.setRol(usuario.getRol());

        log.info("Actualizando usuario con id {}", id);
        return usuarioRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id: " + id);
        }
        log.info("Eliminando usuario con id {}", id);
        usuarioRepository.deleteById(id);
    }
}
