package com.masterbikes.ventas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "venta")
public class VentaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a 0")
    private Long idCliente;

    @Column(name = "fecha_venta", nullable = false)
    @NotNull(message = "La fecha de venta es obligatoria")
    private LocalDate fechaVenta;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a 0")
    private BigDecimal total;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    private String estado;
}
