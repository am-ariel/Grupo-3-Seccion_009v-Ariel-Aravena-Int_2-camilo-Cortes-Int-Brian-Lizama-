package com.masterbikes.bicicletas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "bicicleta")
public class BicicletaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 50, message = "El codigo no puede superar 50 caracteres")
    private String codigo;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 100, message = "El tipo no puede superar 100 caracteres")
    private String tipo;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    private String estado;

    @Column(name = "tarifa_hora", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "La tarifa por hora es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La tarifa por hora debe ser mayor a 0")
    private BigDecimal tarifaHora;
}
