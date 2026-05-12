package com.masterbikes.despachos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Long idVenta;

    @Column(nullable = false, length = 250)
    private String direccion;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(name = "fecha_estimada", nullable = false)
    private LocalDate fechaEstimada;
}
