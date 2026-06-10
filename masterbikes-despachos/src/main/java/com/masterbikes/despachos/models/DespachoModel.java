package com.masterbikes.despachos.models;

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
@Table(name = "despacho")
public class DespachoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_venta", nullable = false)
    @NotNull(message = "El id de la venta es obligatorio")
    @Positive(message = "El id de la venta debe ser mayor a 0")
    private Long idVenta;

    @Column(nullable = false, length = 250)
    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 250, message = "La direccion no puede superar 250 caracteres")
    private String direccion;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 50, message = "El estado no puede superar 50 caracteres")
    private String estado;

    @Column(name = "fecha_estimada", nullable = false)
    @NotNull(message = "La fecha estimada es obligatoria")
    private LocalDate fechaEstimada;
}
