package com.masterbikes.arriendos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "arriendo")
public class ArriendoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a 0")
    private Long idCliente;

    @Column(name = "id_bicicleta", nullable = false)
    @NotNull(message = "El id de la bicicleta es obligatorio")
    @Positive(message = "El id de la bicicleta debe ser mayor a 0")
    private Long idBicicleta;

    @Column(name = "fecha_inicio", nullable = false)
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo debe ser mayor a 0")
    private BigDecimal costo;
}
