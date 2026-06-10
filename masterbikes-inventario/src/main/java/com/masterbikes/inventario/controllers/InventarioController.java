package com.masterbikes.inventario.controllers;

import java.util.List;
import java.util.Optional;

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

import com.masterbikes.inventario.models.InventarioModel;
import com.masterbikes.inventario.services.InventarioService;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public List<InventarioModel> listar() {
        return inventarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventarioModel> obtenerPorId(@PathVariable Long id) {
        Optional<InventarioModel> inventario = inventarioService.obtenerPorId(id);
        return inventario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InventarioModel> guardar(@Valid @RequestBody InventarioModel inventario) {
        // El service decide si el producto requiere aumento segun el stock.
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.guardar(inventario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventarioModel> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioModel inventario) {
        return ResponseEntity.ok(inventarioService.actualizar(id, inventario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/aumento/{aumento}")
    public ResponseEntity<List<InventarioModel>> obtenerPorAumento(@PathVariable Boolean aumento) {
    return ResponseEntity.ok(inventarioService.obtenerPorAumento(aumento));
}
}
