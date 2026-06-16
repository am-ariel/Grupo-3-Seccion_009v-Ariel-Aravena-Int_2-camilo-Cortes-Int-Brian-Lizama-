package com.masterbikes.atencion.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "atencion")
public class AtencionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser mayor a 0")
    private Long idCliente;

    @Column(nullable = false)
    @NotNull(message = "El id de la bicicleta es obligatorio")
    @Positive(message = "El id de la bicicleta debe ser mayor a 0")
    private Long idBicicleta;

    @Column(nullable = false)
    @NotNull(message = "La fecha de ingreso es obligatoria")
    private LocalDate fechaIngreso;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El tipo de servicio es obligatorio")
    @Size(max = 100, message = "El tipo de servicio no puede superar 100 caracteres")
    private String tipoServicio;

    @Column(nullable = false, length = 500)
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String descripcion;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    private String estado;
}
