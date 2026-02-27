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