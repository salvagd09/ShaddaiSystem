USE shaddaiSystem;
Select * from roles;
INSERT INTO Usuarios(Nombres,Apellidos,Contrasena,Rol,username) VALUES ("Salvador Vasco","Goicochea Díaz","1234510",1,"salvagd09"),("Jhossel Bryan","Paredes Rojas","abcde59",2,"jhossel2510"),
("Álvaro Ian","Ávalos Alva","Alva19",3,"alvaAva22");
INSERT INTO Categoria (nombre_Categoria) VALUES 
('Descartables'),
('Bolsas'),
('Envases'),
('Tapas y Tapones'),
('Mangueras'),
('Tuberías'),
('Embalaje'),
('Utensilios de Cocina'),
('Contenedores'),
('Film y Coberturas');
SHOW CREATE TABLE Venta;
Select * from Categoria;
INSERT INTO Producto (Codigo_Producto, Nombre, Stock_Tienda, Stock_Almacen, Unidad_Medida, Precio, Stock_Minimo, ID_Categoria)
VALUES 
('PROD-001', 'Vasos de Plástico 12oz', 50, 200, 'Paquete', 5.50, 20, 1),
('PROD-002', 'Platos Descartables Nro 8', 30, 150, 'Caja', 8.20, 15, 1),
('PROD-003', 'Bolsas de Basura 50x70', 100, 500, 'Millar', 15.00, 50, 2);
INSERT INTO Producto (Codigo_Producto, Nombre, Stock_Tienda, Stock_Almacen, Unidad_Medida, Precio, Stock_Minimo, ID_Categoria)
VALUES ('PROD-004', 'Tapa Rosca 28mm Blanca',300, 1000,'Unidad',  0.30, 100, 4),
('PROD-005', 'Manguera Flexible 1/2 pulg.',60, 200, 'Metro',   4.20,  20, 5),
('PROD-006', 'Tubería PVC 3/4 pulg. x 3m',80, 250, 'Unidad',  12.00, 25, 6),
('PROD-007', 'Cinta de Embalaje Transparente',100, 350, 'Rollo',   2.80,  30, 7),
('PROD-008', 'Cuchara Plástica Reutilizable',180, 600, 'Unidad',  0.90,  60, 8),
('PROD-009', 'Contenedor Hermético 2 Litros',90, 300, 'Unidad',  6.50,  30, 9),
('PROD-010', 'Film Estirable Transparente 30cm',75, 280, 'Rollo',   8.00,  25, 10);