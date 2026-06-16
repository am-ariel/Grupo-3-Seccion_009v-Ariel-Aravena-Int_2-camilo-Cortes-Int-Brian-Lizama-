package com.masterbikes.atencion.controllers;

import com.masterbikes.atencion.models.AtencionModel;
import com.masterbikes.atencion.services.AtencionService;
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
@RequestMapping("/api/v1/atenciones")
public class AtencionController {

    private final AtencionService atencionService;

    public AtencionController(AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    @GetMapping
    public List<AtencionModel> listar() {
        return atencionService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtencionModel> obtenerPorId(@PathVariable Long id) {
        Optional<AtencionModel> atencion = atencionService.obtenerPorId(id);
        return atencion.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{idCliente}")
    public List<AtencionModel> obtenerPorCliente(@PathVariable Long idCliente) {
        return atencionService.obtenerPorCliente(idCliente);
    }

    @PostMapping
    public ResponseEntity<AtencionModel> guardar(@Valid @RequestBody AtencionModel atencion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atencionService.guardar(atencion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtencionModel> actualizar(@PathVariable Long id, @Valid @RequestBody AtencionModel atencion) {
        return ResponseEntity.ok(atencionService.actualizar(id, atencion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        atencionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
