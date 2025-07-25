/**CREE LA BASE DE DATOS PARA LAS VENTAS**/
CREATE DATABASE VENTAS;
		
CREATE TABLE producto(
ID_Codigo INT,
mombre varchar(100), 
Precio numeric (10,2),
ID_inventario INT,
ID_categoria INT,
PRIMARY KEY(ID_Codigo, ID_inventario, ID_categoria)
);

CREATE TABLE categoria(
ID_categoria INT PRIMARY KEY, 
Nombre_Categoria VARCHAR(100)
); 
INSERT INTO categoria (ID_categoria, nombre_categoria) VALUES
(1, 'COMPUTADORAS'),
(2, 'CELULARES'),
(3, 'TELEVISORES'),
(4, 'AUDIO'),
(5, 'CONSOLAS'),
(6, 'ACCESORIOS');



