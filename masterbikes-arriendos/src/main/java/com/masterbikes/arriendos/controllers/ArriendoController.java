package com.masterbikes.arriendos.controllers;

import com.masterbikes.arriendos.models.ArriendoModel;
import com.masterbikes.arriendos.services.ArriendoService;
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
@RequestMapping("/api/v1/arriendos")
public class ArriendoController {

    private final ArriendoService arriendoService;

    public ArriendoController(ArriendoService arriendoService) {
        this.arriendoService = arriendoService;
    }

    @GetMapping
    public List<ArriendoModel> listar() {
        return arriendoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArriendoModel> obtenerPorId(@PathVariable Long id) {
        Optional<ArriendoModel> arriendo = arriendoService.obtenerPorId(id);
        return arriendo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ArriendoModel> guardar(@Valid @RequestBody ArriendoModel arriendo) {
        // El service revisa fechas y luego guarda el arriendo.
        return ResponseEntity.status(HttpStatus.CREATED).body(arriendoService.guardar(arriendo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArriendoModel> actualizar(@PathVariable Long id, @Valid @RequestBody ArriendoModel arriendo) {
        return ResponseEntity.ok(arriendoService.actualizar(id, arriendo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        arriendoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
