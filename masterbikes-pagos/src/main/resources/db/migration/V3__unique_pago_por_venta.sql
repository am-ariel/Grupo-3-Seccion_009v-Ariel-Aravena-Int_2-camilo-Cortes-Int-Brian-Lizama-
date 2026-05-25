ALTER TABLE pago
ADD CONSTRAINT uk_pago_id_venta UNIQUE (id_venta);
