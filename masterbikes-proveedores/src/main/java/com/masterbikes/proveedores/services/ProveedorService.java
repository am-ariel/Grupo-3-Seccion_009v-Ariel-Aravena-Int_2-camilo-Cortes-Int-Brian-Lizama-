package com.masterbikes.proveedores.services;

import com.masterbikes.proveedores.models.ProveedorModel;
import com.masterbikes.proveedores.repositories.ProveedorRepository;
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
public class ProveedorService {

    private static final Logger log = LoggerFactory.getLogger(ProveedorService.class);

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<ProveedorModel> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Optional<ProveedorModel> obtenerPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Optional<ProveedorModel> obtenerPorRut(String rut) {
        return proveedorRepository.findByRut(rut);
    }

    public ProveedorModel guardar(ProveedorModel proveedor) {
        // Regla simple: el RUT identifica al proveedor y no debe repetirse.
        proveedorRepository.findByRut(proveedor.getRut()).ifPresent(existente -> {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un proveedor con el rut: " + proveedor.getRut()
            );
        });
        log.info("Guardando proveedor con rut {}", proveedor.getRut());
        return proveedorRepository.save(proveedor);
    }

    public ProveedorModel actualizar(Long id, ProveedorModel proveedor) {
        ProveedorModel existente = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el proveedor con id: " + id));

        proveedorRepository.findByRut(proveedor.getRut()).ifPresent(proveedorConRut -> {
            if (!Objects.equals(proveedorConRut.getId(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe otro proveedor con el rut: " + proveedor.getRut());
            }
        });

        existente.setRut(proveedor.getRut());
        existente.setNombre(proveedor.getNombre());
        existente.setCorreo(proveedor.getCorreo());
        existente.setTelefono(proveedor.getTelefono());
        existente.setDireccion(proveedor.getDireccion());

        log.info("Actualizando proveedor con id {}", id);
        return proveedorRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el proveedor con id: " + id);
        }
        log.info("Eliminando proveedor con id {}", id);
        proveedorRepository.deleteById(id);
    }
}
