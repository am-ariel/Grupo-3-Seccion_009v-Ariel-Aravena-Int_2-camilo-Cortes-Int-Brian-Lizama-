package com.masterbikes.clientes.controllers;

import com.masterbikes.clientes.models.ClienteModel;
import com.masterbikes.clientes.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteModel> listar() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> obtenerPorId(@PathVariable Long id) {
        Optional<ClienteModel> cliente = clienteService.obtenerPorId(id);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<ClienteModel> obtenerPorRut(@PathVariable String rut) {
        Optional<ClienteModel> cliente = clienteService.obtenerPorRut(rut);
        return cliente.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteModel> guardar(@Valid @RequestBody ClienteModel cliente) {
        // El controller solo recibe la peticion y deja la logica al service.
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.guardar(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteModel cliente) {
        return ResponseEntity.ok(clienteService.actualizar(id, cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
