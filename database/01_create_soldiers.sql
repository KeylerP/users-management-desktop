-- Tabla de soldados
-- Ejecutar en la base de datos configurada en db.name

CREATE TABLE IF NOT EXISTS soldiers (
    id VARCHAR(64) PRIMARY KEY,
    code VARCHAR(50) NOT NULL,
    identity_document VARCHAR(40) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    second_name VARCHAR(100) NOT NULL,
    first_surname VARCHAR(100) NOT NULL,
    second_surname VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    gender VARCHAR(30) NOT NULL,
    initial_height DOUBLE NOT NULL,
    initial_weight DOUBLE NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(40) NOT NULL,
    email VARCHAR(255) NOT NULL,
    military_rank VARCHAR(100) NOT NULL
);