package com.masterbikes.clientes.services;

import com.masterbikes.clientes.models.ClienteModel;
import com.masterbikes.clientes.repositories.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<ClienteModel> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteModel> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<ClienteModel> obtenerPorRut(String rut) {
        return clienteRepository.findByRut(rut);
    }

    public ClienteModel guardar(ClienteModel cliente) {
        // Regla simple: el RUT no se puede repetir porque identifica al cliente.
        if (clienteRepository.existsByRut(cliente.getRut())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente con el rut: " + cliente.getRut());
        }
        log.info("Guardando cliente con rut {}", cliente.getRut());
        return clienteRepository.save(cliente);
    }

    public ClienteModel actualizar(Long id, ClienteModel cliente) {
        ClienteModel existente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente con id: " + id));

        clienteRepository.findByRut(cliente.getRut()).ifPresent(clienteConRut -> {
            if (!Objects.equals(clienteConRut.getId(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe otro cliente con el rut: " + cliente.getRut());
            }
        });

        existente.setRut(cliente.getRut());
        existente.setNombre(cliente.getNombre());
        existente.setApellidos(cliente.getApellidos());
        existente.setFechaNacimiento(cliente.getFechaNacimiento());
        existente.setCorreo(cliente.getCorreo());

        log.info("Actualizando cliente con id {}", id);
        return clienteRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente con id: " + id);
        }
        log.info("Eliminando cliente con id {}", id);
        clienteRepository.deleteById(id);
    }
}
