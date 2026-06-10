package com.masterbikes.pagos.models;

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
@Table(name = "pago")
public class PagoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_venta", nullable = false)
    @NotNull(message = "El id de la venta es obligatorio")
    @Positive(message = "El id de la venta debe ser mayor a 0")
    private Long idVenta;

    @Column(name = "fecha_pago", nullable = false)
    @NotNull(message = "La fecha de pago es obligatoria")
    private LocalDate fechaPago;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El metodo de pago es obligatorio")
    @Size(max = 50, message = "El metodo no puede superar 50 caracteres")
    private String metodo;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    private String estado;
}
