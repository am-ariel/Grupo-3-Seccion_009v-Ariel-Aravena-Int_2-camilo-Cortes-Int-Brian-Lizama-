package com.masterbikes.proveedores.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "proveedor")
public class ProveedorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 13)
    @NotBlank(message = "El rut es obligatorio")
    @Size(max = 13, message = "El rut no puede superar 13 caracteres")
    private String rut;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
    private String nombre;

    @Column(nullable = false, length = 150)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato valido")
    @Size(max = 150, message = "El correo no puede superar 150 caracteres")
    private String correo;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "El telefono es obligatorio")
    @Size(max = 30, message = "El telefono no puede superar 30 caracteres")
    private String telefono;

    @Column(nullable = false, length = 250)
    @NotBlank(message = "La direccion es obligatoria")
    @Size(max = 250, message = "La direccion no puede superar 250 caracteres")
    private String direccion;
}
