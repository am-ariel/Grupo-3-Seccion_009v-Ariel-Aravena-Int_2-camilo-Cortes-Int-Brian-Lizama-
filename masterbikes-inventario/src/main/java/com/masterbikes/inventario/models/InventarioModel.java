package com.masterbikes.inventario.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "inventario")
public class InventarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_producto", nullable = false, length = 150)
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 150, message = "El nombre del producto no puede superar 150 caracteres")
    private String nombreProducto;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "La categoria es obligatoria")
    @Size(max = 100, message = "La categoria no puede superar 100 caracteres")
    private String categoria;

    @Column(nullable = false)
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Column(name = "stock_minimo", nullable = false)
    @NotNull(message = "El stock minimo es obligatorio")
    @Min(value = 0, message = "El stock minimo no puede ser negativo")
    private Integer stockMinimo;

    @Column(name = "aumento",nullable = false)
    @NotNull(message = "Debe indicar si requiere aumento")
    private Boolean aumento;

}
