CREATE DATABASE IF NOT EXISTS uasd_catalogo
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE uasd_catalogo;

DROP TABLE IF EXISTS carreras;
DROP TABLE IF EXISTS escuelas;

CREATE TABLE escuelas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

CREATE TABLE carreras (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(120) NOT NULL,
  grado VARCHAR(40) NOT NULL,
  id_escuela INT NOT NULL,
  FOREIGN KEY (id_escuela) REFERENCES escuelas(id)
);

INSERT INTO escuelas (nombre) VALUES
 ('Ingeniería y Arquitectura'),        -- 1
 ('Ciencias Económicas y Sociales'),   -- 2 (negocios)
 ('Artes'),                            -- 3
 ('Ciencias de la Salud'),             -- 4
 ('Ciencias'),                         -- 5
 ('Humanidades'),                      -- 6
 ('Ciencias Jurídicas y Políticas'),   -- 7
 ('Ciencias Agronómicas y Veterinarias'); -- 8

INSERT INTO carreras (nombre, grado, id_escuela) VALUES
 ('Arquitectura','Licenciatura',1),
 ('Ingeniería Civil','Ingeniería',1),
 ('Ingeniería Eléctrica','Ingeniería',1),
 ('Ingeniería Mecánica','Ingeniería',1),
 ('Ingeniería Industrial','Ingeniería',1),
 ('Agrimensura','Ingeniería',1),
 ('Administración de Empresas','Licenciatura',2),
 ('Contabilidad','Licenciatura',2),
 ('Mercadotecnia','Licenciatura',2),
 ('Economía','Licenciatura',2),
 ('Artes Plásticas','Licenciatura',3),
 ('Música','Licenciatura',3),
 ('Teatro','Licenciatura',3),
 ('Medicina','Doctorado',4),
 ('Odontología','Doctorado',4),
 ('Enfermería','Licenciatura',4),
 ('Bioanálisis','Licenciatura',4),
 ('Informática','Licenciatura',5),
 ('Matemática','Licenciatura',5),
 ('Física','Licenciatura',5),
 ('Psicología','Licenciatura',6),
 ('Comunicación Social','Licenciatura',6),
 ('Derecho','Licenciatura',7),
 ('Agronomía','Ingeniería',8),
 ('Medicina Veterinaria','Doctorado',8);