/**CREE LA BASE DE DATOS PARA LAS VENTAS**/
CREATE DATABASE VENTAS;

CREATE TABLE ventas.estados(
ID_Estado INT,
Descripcion VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE ventas.usuarios(
ID_Usuario INT,
Nombre VARCHAR(100) NOT NULL,
Direccion INT NOT NULL,
ID_Tipo_Usuario INT NOT NULL,
ID_Estado INT NOT NULL,
PRIMARY KEY (ID_Usuario)
);

ALTER TABLE ventas.USUARIOS
ADD COLUMN primer_apellido VARCHAR(50),
ADD COLUMN segundo_apellido VARCHAR(50),
ADD COLUMN correo VARCHAR(100);

CREATE TABLE ventas.tipo_usuario(
ID_Tipo_Usuario INT, 
Descripcionn_Tipo_Usuario VARCHAR(50) NOT NULL,
ID_Estado INT NOT NULL,
PRIMARY KEY(ID_Tipo_Usuario)
);

CREATE TABLE ventas.producto(
ID_Codigo INT,
mombre varchar(100), 
Precio numeric (10,2),
ID_inventario INT,
ID_categoria INT,
PRIMARY KEY(ID_Codigo, ID_inventario, ID_categoria)
);

CREATE TABLE ventas.categoria(
ID_categoria INT PRIMARY KEY, 
Nombre_Categoria VARCHAR(100)
); 
INSERT INTO ventas.categoria (ID_categoria, nombre_categoria) VALUES
(1, 'COMPUTADORAS'),
(2, 'CELULARES'),
(3, 'TELEVISORES'),
(4, 'AUDIO'),
(5, 'CONSOLAS'),
(6, 'ACCESORIOS');

CREATE TABLE ventas.inventario(
ID_Inventario INT AUTO_INCREMENT,
ID_Categoria VARCHAR(100),
Nombre_Producto VARCHAR(100),
Precio NUMERIC(10,2),
Cantidad INT, 
 PRIMARY KEY(ID_Inventario)
 );  