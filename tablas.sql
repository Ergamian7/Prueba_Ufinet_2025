CREATE TABLE usuarios (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    activo BIT DEFAULT 1,
    fecha_creacion DATETIME DEFAULT GETDATE()
);

CREATE TABLE roles (
    id INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE usuarios_roles (
    usuario_id INT,
    rol_id INT,
    PRIMARY KEY (usuario_id, rol_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (rol_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- Insertar roles por defecto
INSERT INTO roles (nombre) VALUES ('USER'), ('ADMIN');
