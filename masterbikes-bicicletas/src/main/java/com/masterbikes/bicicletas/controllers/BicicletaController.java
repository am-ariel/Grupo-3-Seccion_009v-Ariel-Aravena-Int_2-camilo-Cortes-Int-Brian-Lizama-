package com.masterbikes.bicicletas.controllers;

import com.masterbikes.bicicletas.models.BicicletaModel;
import com.masterbikes.bicicletas.services.BicicletaService;
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
@RequestMapping("/api/v1/bicicletas")
public class BicicletaController {

    private final BicicletaService bicicletaService;

    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping
    public List<BicicletaModel> listar() {
        return bicicletaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BicicletaModel> obtenerPorId(@PathVariable Long id) {
        Optional<BicicletaModel> bicicleta = bicicletaService.obtenerPorId(id);
        return bicicleta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BicicletaModel> guardar(@Valid @RequestBody BicicletaModel bicicleta) {
        // Crear bicicleta: el service valida reglas y guarda usando JPA.
        return ResponseEntity.status(HttpStatus.CREATED).body(bicicletaService.guardar(bicicleta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BicicletaModel> actualizar(@PathVariable Long id, @Valid @RequestBody BicicletaModel bicicleta) {
        return ResponseEntity.ok(bicicletaService.actualizar(id, bicicleta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        bicicletaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
