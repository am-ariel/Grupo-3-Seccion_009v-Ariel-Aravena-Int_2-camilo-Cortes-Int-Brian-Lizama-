CREATE TABLE IF NOT EXISTS atencion (
    id BIGINT NOT NULL AUTO_INCREMENT,
    id_cliente BIGINT NOT NULL,
    id_bicicleta BIGINT NOT NULL,
    fecha_ingreso DATE NOT NULL,
    tipo_servicio VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);
