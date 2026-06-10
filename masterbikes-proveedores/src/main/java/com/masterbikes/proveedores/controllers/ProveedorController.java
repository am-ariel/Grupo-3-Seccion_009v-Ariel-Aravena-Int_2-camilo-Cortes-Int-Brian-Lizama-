package com.masterbikes.proveedores.controllers;

import com.masterbikes.proveedores.models.ProveedorModel;
import com.masterbikes.proveedores.services.ProveedorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public List<ProveedorModel> listar() {
        return proveedorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorModel> obtenerPorId(@PathVariable Long id) {
        Optional<ProveedorModel> proveedor = proveedorService.obtenerPorId(id);
        return proveedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ProveedorModel> obtenerPorRut(@PathVariable String rut) {
        Optional<ProveedorModel> proveedor = proveedorService.obtenerPorRut(rut);
        return proveedor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProveedorModel> guardar(@Valid @RequestBody ProveedorModel proveedor) {
        // El service verifica que no exista otro proveedor con el mismo RUT.
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.guardar(proveedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorModel> actualizar(@PathVariable Long id, @Valid @RequestBody ProveedorModel proveedor) {
        return ResponseEntity.ok(proveedorService.actualizar(id, proveedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
