package com.masterbikes.proveedores.services;

import com.masterbikes.proveedores.models.ProveedorModel;
import com.masterbikes.proveedores.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProveedorService {

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
        return proveedorRepository.save(proveedor);
    }

    public void eliminar(Long id) {
        proveedorRepository.deleteById(id);
    }
}
