package com.masterbikes.pagos.client;

import com.masterbikes.pagos.dtos.VentaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "venta-client",
        url = "${ventas.service.url}"
)
public interface VentaClient {

    @GetMapping("/api/v1/ventas/{id}")
    VentaResponse obtenerVentaPorId(@PathVariable("id") Long id);
}
