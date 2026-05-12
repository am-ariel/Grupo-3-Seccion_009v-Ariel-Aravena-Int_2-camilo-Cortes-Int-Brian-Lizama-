package com.masterbikes.bicicletas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String codigo;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(nullable = false, length = 50)
    private String estado;

    @Column(name = "tarifa_hora", nullable = false, precision = 10, scale = 2)
    private BigDecimal tarifaHora;
}
