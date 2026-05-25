package com.masterbikes.pagos.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class VentaResponse {

    private Long id;
    private Long idCliente;
    private LocalDate fechaVenta;
    private BigDecimal total;
    private String estado;
}
