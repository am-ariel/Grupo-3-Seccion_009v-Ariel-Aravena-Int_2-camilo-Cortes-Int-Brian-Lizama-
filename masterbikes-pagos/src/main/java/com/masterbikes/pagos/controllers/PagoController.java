package com.masterbikes.pagos.controllers;

import com.masterbikes.pagos.models.PagoModel;
import com.masterbikes.pagos.services.PagoService;
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
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping
    public List<PagoModel> listar() {
        return pagoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoModel> obtenerPorId(@PathVariable Long id) {
        Optional<PagoModel> pago = pagoService.obtenerPorId(id);
        return pago.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PagoModel> guardar(@Valid @RequestBody PagoModel pago) {
        // Pago consulta ventas antes de guardar, asi evitamos pagar una venta inexistente.
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.guardar(pago));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoModel> actualizar(@PathVariable Long id, @Valid @RequestBody PagoModel pago) {
        return ResponseEntity.ok(pagoService.actualizar(id, pago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
