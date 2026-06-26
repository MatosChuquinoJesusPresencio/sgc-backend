-- Seed data for controller tests
-- Password "123456" BCrypt hash

-- Register unaccent alias for H2 (pass-through, test data has no accents)
CREATE ALIAS IF NOT EXISTS unaccent AS 'String unaccent(String s) { return s; }';

SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE log_prestamo_carrito;
TRUNCATE TABLE log_acceso_vehicular;
TRUNCATE TABLE carrito;
TRUNCATE TABLE vehiculo;
TRUNCATE TABLE estacionamiento;
TRUNCATE TABLE inquilino;
TRUNCATE TABLE apartamento;
TRUNCATE TABLE piso;
TRUNCATE TABLE torre;
TRUNCATE TABLE token;
TRUNCATE TABLE usuario;
TRUNCATE TABLE configuracion;
TRUNCATE TABLE condominio;
TRUNCATE TABLE ciudad;
TRUNCATE TABLE pais;
TRUNCATE TABLE moneda;
SET REFERENTIAL_INTEGRITY TRUE;

-- Reset H2 identity sequences after TRUNCATE + explicit ID inserts
ALTER TABLE usuario ALTER COLUMN id RESTART WITH 100;
ALTER TABLE condominio ALTER COLUMN id RESTART WITH 100;
ALTER TABLE configuracion ALTER COLUMN id RESTART WITH 100;
ALTER TABLE torre ALTER COLUMN id RESTART WITH 100;
ALTER TABLE piso ALTER COLUMN id RESTART WITH 100;
ALTER TABLE apartamento ALTER COLUMN id RESTART WITH 100;

INSERT INTO moneda (id, nombre, codigo, simbolo) VALUES (1, 'Sol Peruano', 'PEN', 'S/');
INSERT INTO pais (id, nombre, codigo_iso, moneda_id) VALUES (1, 'Perú', 'PE', 1);
INSERT INTO ciudad (id, nombre, pais_id) VALUES (1, 'Lima', 1);
INSERT INTO condominio (id, nombre, pais_id, ciudad_id, direccion, activo, fecha_creacion)
VALUES (1, 'Residencial Test', 1, 1, 'Av. Test 123', TRUE, NOW());
INSERT INTO configuracion (id, condominio_id, max_autos, max_motos, penalizacion_por_min, max_tiempo_prestamo_min, max_estacionamientos_por_apartamento, max_carritos_por_apartamento, max_vehiculos_por_propietario, max_inquilinos_por_apartamento)
VALUES (1, 1, 10, 5, 0.50, 120, 2, 1, 3, 4);
INSERT INTO usuario (id, nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES (1, 'Super', 'Admin', 'super@test.com', '+51999000001', 'SUPER_ADMINISTRADOR', TRUE,
        '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, NULL, NOW());
INSERT INTO usuario (id, nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES (2, 'Admin', 'Condominio', 'admin@test.com', '+51999000002', 'ADMINISTRADOR_CONDOMINIO', TRUE,
        '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW());
INSERT INTO usuario (id, nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES (3, 'Propietario', 'Test', 'propietario@test.com', '+51999000003', 'PROPIETARIO', TRUE,
        '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW());
INSERT INTO usuario (id, nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES (4, 'Agente', 'Seguridad', 'agente@test.com', '+51999000004', 'AGENTE_SEGURIDAD', TRUE,
        '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW());
INSERT INTO usuario (id, nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES (5, 'Inactivo', 'Usuario', 'inactivo@test.com', '+51999000005', 'PROPIETARIO', FALSE,
        '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW());
INSERT INTO torre (id, nombre, condominio_id) VALUES (1, 'Torre A', 1);
INSERT INTO piso (id, numero, torre_id) VALUES (1, 1, 1);
INSERT INTO apartamento (id, numero, derecho_estacionamiento, metraje, piso_id, propietario_id)
VALUES (1, 101, TRUE, 85.50, 1, 3);
