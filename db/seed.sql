-- =============================================================================
-- SGC — Seed Data Script
-- =============================================================================
-- Genera datos de prueba para los 4 roles del sistema con dos condominios
-- completos: "Residencial Los Olivos" y "Edificio San Martín".
--
-- Orden de inserción respetando FK:
--   moneda → pais → ciudad → condominio → configuracion
--   → usuario → torre → piso → apartamento → inquilino
--   → estacionamiento → vehiculo → carrito
--   → log_acceso_vehicular → log_prestamo_carrito → token
--
-- PASSWORDS (BCrypt, hash de "123456"):
--   $2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.
-- =============================================================================

-- =============================================================================
-- EXTENSIONES
-- =============================================================================
CREATE EXTENSION IF NOT EXISTS unaccent;

-- =============================================================================
-- LIMPIEZA: elimina datos previos en orden inverso al de inserción
-- =============================================================================
TRUNCATE TABLE log_prestamo_carrito RESTART IDENTITY CASCADE;
TRUNCATE TABLE log_acceso_vehicular RESTART IDENTITY CASCADE;
TRUNCATE TABLE carrito RESTART IDENTITY CASCADE;
TRUNCATE TABLE vehiculo RESTART IDENTITY CASCADE;
TRUNCATE TABLE estacionamiento RESTART IDENTITY CASCADE;
TRUNCATE TABLE inquilino RESTART IDENTITY CASCADE;
TRUNCATE TABLE apartamento RESTART IDENTITY CASCADE;
TRUNCATE TABLE piso RESTART IDENTITY CASCADE;
TRUNCATE TABLE torre RESTART IDENTITY CASCADE;
TRUNCATE TABLE token RESTART IDENTITY CASCADE;
TRUNCATE TABLE usuario RESTART IDENTITY CASCADE;
TRUNCATE TABLE configuracion RESTART IDENTITY CASCADE;
TRUNCATE TABLE condominio RESTART IDENTITY CASCADE;
TRUNCATE TABLE ciudad RESTART IDENTITY CASCADE;
TRUNCATE TABLE pais RESTART IDENTITY CASCADE;
TRUNCATE TABLE moneda RESTART IDENTITY CASCADE;
-- =============================================================================

-- 1. MONEDA -------------------------------------------------------------------
INSERT INTO moneda (nombre, codigo, simbolo) VALUES
    ('Sol Peruano', 'PEN', 'S/'),
    ('US Dólar', 'USD', '$');

-- 2. PAIS ---------------------------------------------------------------------
INSERT INTO pais (nombre, codigo_iso, moneda_id) VALUES
    ('Perú', 'PE', 1);

-- 3. CIUDAD -------------------------------------------------------------------
INSERT INTO ciudad (nombre, pais_id) VALUES
    ('Lima', 1);

-- 4. CONDOMINIO ---------------------------------------------------------------
INSERT INTO condominio (nombre, pais_id, ciudad_id, direccion, activo, fecha_creacion)
VALUES
    ('Residencial Los Olivos', 1, 1, 'Av. Primavera 1234, Surco', TRUE,  NOW() - INTERVAL '180 days'),
    ('Edificio San Martín',    1, 1, 'Jr. de la Unión 567, Centro', TRUE, NOW() - INTERVAL '90 days');

-- 5. CONFIGURACION (por condominio) -------------------------------------------
INSERT INTO configuracion (
    condominio_id,
    max_autos, max_motos,
    penalizacion_por_min, max_tiempo_prestamo_min,
    max_estacionamientos_por_apartamento, max_carritos_por_apartamento,
    max_vehiculos_por_propietario, max_inquilinos_por_apartamento
) VALUES
    (1, 4, 2, 0.50, 120, 2, 1, 3, 4),
    (2, 4, 2, 0.25,  90, 1, 1, 2, 2);

-- 6. USUARIOS -----------------------------------------------------------------
-- Contraseña por defecto: 123456
-- BCrypt hash: $2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.

-- Super Administrador (sin condominio)
INSERT INTO usuario (nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES ('Carlos', 'Gutiérrez Mendoza', 'admin@sgc.com', '+51999000001', 'SUPER_ADMINISTRADOR', TRUE,
        '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, NULL, NOW() - INTERVAL '200 days');

-- Administradores de condominio
INSERT INTO usuario (nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES
    ('María', 'López Rivas', 'maria.lopez@olivos.com', '+51999000002', 'ADMINISTRADOR_CONDOMINIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '170 days'),
    ('Pedro', 'Sánchez Vega', 'pedro.sanchez@sanmartin.com', '+51999000003', 'ADMINISTRADOR_CONDOMINIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 2, NOW() - INTERVAL '80 days');

-- Propietarios — Los Olivos (torres A y B)
INSERT INTO usuario (nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES
    ('Luis', 'Fernández Torres',     'luis.fernandez@email.com', '+51999000004', 'PROPIETARIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '160 days'),
    ('Ana', 'Martínez Paredes',      'ana.martinez@email.com',  '+51999000005', 'PROPIETARIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '155 days'),
    ('Jorge', 'Ramírez Vargas',      'jorge.ramirez@email.com', '+51999000006', 'PROPIETARIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '150 days'),
    ('Rosa', 'Castro Delgado',       'rosa.castro@email.com',   '+51999000007', 'PROPIETARIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '145 days'),
    ('Miguel', 'Álvarez Campos',     'miguel.alvarez@email.com','+51999000008', 'PROPIETARIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '140 days');

-- Propietarios — San Martín
INSERT INTO usuario (nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES
    ('Diana', 'Peralta Salas',       'diana.peralta@email.com', '+51999000009', 'PROPIETARIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 2, NOW() - INTERVAL '70 days'),
    ('Ricardo', 'Ortega Flores',     'ricardo.ortega@email.com','+51999000010', 'PROPIETARIO', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 2, NOW() - INTERVAL '65 days');

-- Agentes de seguridad — Los Olivos
INSERT INTO usuario (nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES
    ('José', 'Huamán Quispe',        'jose.huaman@olivos.com',  '+51999000011', 'AGENTE_SEGURIDAD', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '160 days'),
    ('Carmen', 'Vega Morales',       'carmen.vega@olivos.com',  '+51999000012', 'AGENTE_SEGURIDAD', TRUE,
     '$2a$10$S5ZrKGs3kzG3iKewmjrBn.vMrUtgVMY11e3EsRJ8budNF/Q.n.bQ.', TRUE, 1, NOW() - INTERVAL '150 days');

-- Agentes de seguridad — San Martín
INSERT INTO usuario (nombres, apellidos, correo, telefono, rol, activo, contrasena, correo_verificado, condominio_id, fecha_creacion)
VALUES
    ('Alberto', 'Ríos Paredes',      'alberto.rios@sanmartin.com','+51999000013', 'AGENTE_SEGURIDAD', TRUE,
     '$2a$10$Nq9o8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE, 2, NOW() - INTERVAL '70 days');

-- 7. TORRES -------------------------------------------------------------------
INSERT INTO torre (nombre, condominio_id) VALUES
    ('Torre A', 1),
    ('Torre B', 1),
    ('Torre Única', 2);

-- 8. PISOS --------------------------------------------------------------------
-- Los Olivos — Torre A: 5 pisos
INSERT INTO piso (numero, torre_id) VALUES
    (1, 1), (2, 1), (3, 1), (4, 1), (5, 1);
-- Los Olivos — Torre B: 4 pisos
INSERT INTO piso (numero, torre_id) VALUES
    (1, 2), (2, 2), (3, 2), (4, 2);
-- San Martín — Torre Única: 3 pisos
INSERT INTO piso (numero, torre_id) VALUES
    (1, 3), (2, 3), (3, 3);

-- 9. APARTAMENTOS -------------------------------------------------------------
-- Los Olivos — Torre A: 4 aptos por piso (101-104, 201-204, … 501-504)
INSERT INTO apartamento (numero, derecho_estacionamiento, metraje, piso_id, propietario_id) VALUES
    (101, TRUE,  85.50, 1, 4),   -- Luis Fernández (id 4)
    (102, TRUE,  72.00, 1, 5),   -- Ana Martínez (id 5)
    (103, FALSE, 60.00, 1, NULL),
    (104, TRUE,  95.00, 1, NULL),
    (201, TRUE,  85.50, 2, 6),   -- Jorge Ramírez (id 6)
    (202, TRUE,  72.00, 2, NULL),
    (203, FALSE, 60.00, 2, NULL),
    (204, TRUE,  95.00, 2, NULL),
    (301, TRUE,  85.50, 3, 7),   -- Rosa Castro (id 7)
    (302, TRUE,  72.00, 3, NULL),
    (303, FALSE, 60.00, 3, NULL),
    (304, TRUE,  95.00, 3, NULL),
    (401, TRUE,  85.50, 4, 8),   -- Miguel Álvarez (id 8)
    (402, TRUE,  72.00, 4, NULL),
    (403, FALSE, 60.00, 4, NULL),
    (404, TRUE,  95.00, 4, NULL),
    (501, TRUE, 100.00, 5, NULL),
    (502, TRUE,  88.00, 5, NULL),
    (503, FALSE, 65.00, 5, NULL),
    (504, TRUE,  92.00, 5, NULL);

-- Los Olivos — Torre B: 4 aptos por piso (1001-1004, 2001-2004, … 4001-4004)
INSERT INTO apartamento (numero, derecho_estacionamiento, metraje, piso_id, propietario_id) VALUES
    (1001, TRUE, 80.00, 6, NULL),
    (1002, TRUE, 75.00, 6, NULL),
    (1003, TRUE, 68.00, 6, NULL),
    (1004, TRUE, 90.00, 6, NULL),
    (2001, TRUE, 80.00, 7, NULL),
    (2002, TRUE, 75.00, 7, NULL),
    (2003, TRUE, 68.00, 7, NULL),
    (2004, TRUE, 90.00, 7, NULL),
    (3001, TRUE, 80.00, 8, NULL),
    (3002, TRUE, 75.00, 8, NULL),
    (3003, TRUE, 68.00, 8, NULL),
    (3004, TRUE, 90.00, 8, NULL),
    (4001, TRUE, 80.00, 9, NULL),
    (4002, TRUE, 75.00, 9, NULL),
    (4003, TRUE, 68.00, 9, NULL),
    (4004, TRUE, 90.00, 9, NULL);

-- San Martín — Torre Única: 2 aptos por piso
INSERT INTO apartamento (numero, derecho_estacionamiento, metraje, piso_id, propietario_id) VALUES
    (101, TRUE, 65.00, 10, 9),   -- Diana Peralta (id 9)
    (102, TRUE, 70.00, 10, NULL),
    (201, TRUE, 65.00, 11, 10),  -- Ricardo Ortega (id 10)
    (202, TRUE, 70.00, 11, NULL),
    (301, TRUE, 65.00, 12, NULL),
    (302, TRUE, 70.00, 12, NULL);

-- 10. INQUILINOS --------------------------------------------------------------
INSERT INTO inquilino (nombres, apellidos, tipo_documento, numero_documento, apartamento_id) VALUES
    ('Sofía', 'Fernández Castro',  'DNI', '71234567', 1),   -- hija de Luis, apto 101
    ('Carlos', 'Fernández Castro', 'DNI', '72345678', 1),   -- hijo de Luis, apto 101
    ('Elena', 'García Ruiz',       'DNI', '73456789', 2),   -- inquilina, apto 102
    ('Pablo', 'Martínez Rivas',    'DNI', '74567890', 5);   -- inquilino, apto 201

-- 11. ESTACIONAMIENTOS --------------------------------------------------------
-- Los Olivos — 8 spots (compartidos, no vinculados a un apto específico)
INSERT INTO estacionamiento (numero, tipo_vehiculo, capacidad_maxima, cantidad_actual, disponible, apartamento_id, condominio_id) VALUES
    (1,  'AUTO', 2, 1, TRUE,  NULL, 1),
    (2,  'AUTO', 2, 0, TRUE,  NULL, 1),
    (3,  'AUTO', 2, 0, TRUE,  NULL, 1),
    (4,  'MOTO', 4, 0, TRUE,  NULL, 1),
    (5,  'AUTO', 2, 0, TRUE,  NULL, 1),
    (6,  'AUTO', 2, 0, TRUE,  NULL, 1),
    (7,  'MOTO', 4, 0, TRUE,  NULL, 1),
    (8,  'AUTO', 1, 1, FALSE, NULL, 1);

-- San Martín — 4 spots
INSERT INTO estacionamiento (numero, tipo_vehiculo, capacidad_maxima, cantidad_actual, disponible, apartamento_id, condominio_id) VALUES
    (1, 'AUTO', 1, 1, FALSE, NULL, 2),
    (2, 'AUTO', 1, 0, TRUE,  NULL, 2),
    (3, 'MOTO', 2, 0, TRUE,  NULL, 2),
    (4, 'AUTO', 1, 0, TRUE,  NULL, 2);

-- 12. VEHÍCULOS ---------------------------------------------------------------
INSERT INTO vehiculo (marca, color, modelo, placa, tipo, propietario_id, inquilino_id, estacionamiento_id, condominio_id) VALUES
    ('Toyota',   'Blanco',  'Corolla Cross',   'ABC-123', 'AUTO', 4, NULL, 1, 1),  -- Luis F. — dentro (slot 1)
    ('Honda',    'Negro',   'Civic',           'DEF-456', 'AUTO', 5, NULL, NULL, 1), -- Ana M. — ya salió
    ('Yamaha',   'Rojo',    'MT-07',           'GHI-789', 'MOTO', 6, NULL, NULL, 1), -- Jorge R. — ya salió
    ('Nissan',   'Azul',    'Versa',           'MNO-345', 'AUTO', 8, NULL, 8, 1),  -- Miguel A. — dentro (slot 8)
    ('Hyundai',  'Blanco',  'Tucson',          'STU-901', 'AUTO', 9, NULL, 1, 2),  -- Diana P. — dentro (slot 1, San Martín)
    ('Mazda',    'Negro',   'CX-5',            'VWX-234', 'AUTO', 10, NULL, NULL, 2); -- Ricardo O. — salió, sin slot

-- 13. CARRITOS ----------------------------------------------------------------
INSERT INTO carrito (codigo, estado, condominio_id) VALUES
    ('CARR-001', 'DISPONIBLE',   1),
    ('CARR-002', 'EN_USO',       1),
    ('CARR-003', 'MANTENIMIENTO',1),
    ('CARR-004', 'DISPONIBLE',   1),
    ('CARR-005', 'DISPONIBLE',   2),
    ('CARR-006', 'EN_USO',       2);

-- 14. LOG ACCESO VEHICULAR ----------------------------------------------------
INSERT INTO log_acceso_vehicular (placa, ocupante, datos_inquilino, metodo, fecha_entrada, fecha_salida, vehiculo_id, estacionamiento_id, condominio_id) VALUES
    ('ABC-123', 'PROPIETARIO', NULL,               'OCR',  NOW() - INTERVAL '2 hours',   NULL,                    1, 1, 1),
    ('DEF-456', 'PROPIETARIO', NULL,               'MANUAL', NOW() - INTERVAL '5 hours', NOW() - INTERVAL '3 hours', 2, 3, 1),
    ('GHI-789', 'PROPIETARIO', NULL,               'OCR',  NOW() - INTERVAL '1 day',     NOW() - INTERVAL '20 hours', 3, 4, 1),
    ('STU-901', 'PROPIETARIO', NULL,               'OCR',  NOW() - INTERVAL '3 hours',   NULL,                     7, 1, 2),
    ('VWX-234', 'PROPIETARIO', NULL,               'MANUAL', NOW() - INTERVAL '1 day',   NOW() - INTERVAL '10 hours', 8, NULL, 2);

-- 15. LOG PRÉSTAMO CARRITO ----------------------------------------------------
INSERT INTO log_prestamo_carrito (solicitante, nombre_solicitante, dni_solicitante, penalizacion, fecha_prestamo, fecha_devolucion, apartamento_id, carrito_id, inquilino_id, propietario_id, condominio_id) VALUES
    ('PROPIETARIO', 'Luis Fernández Torres',  '12345678', 0,    NOW() - INTERVAL '1 day',   NOW() - INTERVAL '20 hours', 1, 2, NULL, 4, 1),
    ('INQUILINO',   'Sofía Fernández Castro','71234567', 0,    NOW() - INTERVAL '2 days',  NOW() - INTERVAL '1 day',   1, 1, 1, NULL, 1),
    ('PROPIETARIO', 'Diana Peralta Salas',   '23456789', 0,    NOW() - INTERVAL '6 hours', NULL,                       17, 6, NULL, 9, 2);
