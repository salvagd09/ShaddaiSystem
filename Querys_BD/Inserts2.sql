USE shaddaiSystem;
INSERT INTO Categoria (nombre_Categoria) VALUES ('Descartables'), ('Bolsas');
INSERT INTO Producto (Codigo_Producto, Nombre, Stock_Tienda, Stock_Almacen, Unidad_Medida, Precio, Stock_Minimo, ID_Categoria)
VALUES 
('PROD-001', 'Vasos de Plástico 12oz', 50, 200, 'Paquete', 5.50, 20, 1),
('PROD-002', 'Platos Descartables Nro 8', 30, 150, 'Ciento', 8.20, 15, 1),
('PROD-003', 'Bolsas de Basura 50x70', 100, 500, 'Millar', 15.00, 50, 2);
