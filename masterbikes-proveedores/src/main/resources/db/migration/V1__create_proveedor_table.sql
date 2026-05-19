CREATE TABLE IF NOT EXISTS proveedor (
    id BIGINT NOT NULL AUTO_INCREMENT,
    rut VARCHAR(13) NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(30) NOT NULL,
    direccion VARCHAR(250) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_proveedor_rut (rut)
);
