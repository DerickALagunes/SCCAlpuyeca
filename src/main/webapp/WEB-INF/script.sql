drop database scc;

-- Crear la base de datos
CREATE DATABASE scc CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Usar la base de datos
USE scc;

-- Crear la tabla de usuarios
CREATE TABLE usuarios (
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('admin', 'user') NOT NULL
);

-- Tabla: Clientes
CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    gestor VARCHAR(255),
    razon_social VARCHAR(255) 
);

CREATE TABLE costos(
	id_costo INT AUTO_INCREMENT PRIMARY KEY,
    materia VARCHAR(40),
    costo DECIMAL(10,2),
    cliente_empresa VARCHAR(150),
    encargado VARCHAR(150),
    atiende_y_cobra VARCHAR(100)
);

-- Tabla: Vehículos
CREATE TABLE vehiculos (
    id_vehiculo INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(50),
    serie VARCHAR(255),
    id_cliente INT NOT NULL,
	FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE
);

-- Tabla: Verificaciones
CREATE TABLE verificaciones (
    id_verificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_vehiculo INT NOT NULL,
    materia ENUM("HUMO", "MOTRIZ", "ARRASTRE", "GASOLINA", "NULL"),
    verificentro VARCHAR(255),
    precio DECIMAL(12,2),
    fecha_folio DATE DEFAULT (CURRENT_DATE),
    folio Varchar(50),
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo) ON DELETE CASCADE
);

-- Tabla: Transacciones
CREATE TABLE transacciones (
    id_transaccion INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_verificacion INT NOT NULL,    
    tipo_pago ENUM("FACTURA", "DEPOSITO", "EFECTIVO", "NULL"),
    numero_nota VARCHAR(50),
    cotizacion VARCHAR(50),    
    cuenta_deposito VARCHAR(255),
    numero_factura VARCHAR(50),
    pagado ENUM('SI', 'NO'),
    atiende_y_cobra VARCHAR(100),
    fecha_pedido DATE,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE CASCADE,
    FOREIGN KEY (id_verificacion) REFERENCES verificaciones(id_verificacion) ON DELETE CASCADE
);

CREATE TABLE pedidos (
	id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_transaccion INT,
    fecha_envio DATE,
    numero_guia VARCHAR(50),
    recibio VARCHAR(100),
    foto VARCHAR(512),
    FOREIGN KEY (id_transaccion) REFERENCES transacciones(id_transaccion)
);

-- Datos usuarios
INSERT INTO usuarios (nombre_usuario, email, contrasena, tipo_usuario) VALUES 
("Verificentro", "registro@gmail.com", sha2("registro",256),"user"),
("Administrador", "cobro@gmail.com", sha2("cobro",256),"admin");
