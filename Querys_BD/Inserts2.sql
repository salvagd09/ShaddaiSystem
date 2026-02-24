USE shaddaiSystem;
INSERT INTO Categoria (nombre_Categoria) VALUES ('Descartables'), ('Bolsas');
INSERT INTO Producto (Codigo_Producto, Nombre, Stock_Tienda, Stock_Almacen, Unidad_Medida, Precio, Stock_Minimo, ID_Categoria)
VALUES 
('PROD-001', 'Vasos de Plástico 12oz', 50, 200, 'Paquete', 5.50, 20, 1),
('PROD-002', 'Platos Descartables Nro 8', 30, 150, 'Ciento', 8.20, 15, 1),
('PROD-003', 'Bolsas de Basura 50x70', 100, 500, 'Millar', 15.00, 50, 2);

CREATE PROCEDURE Validar_Cantidad_Tienda(
    IN P_Codigo_Producto VARCHAR(50),
    IN P_Cantidad INT
)
BEGIN
    DECLARE v_stock_actual INT;
    DECLARE v_Nombre VARCHAR(200);
    DECLARE v_Unidad_Medida VARCHAR(50);
    DECLARE v_Precio DECIMAL(10,2);

    SELECT Stock_Tienda, Nombre, Unidad_Medida, Precio 
    INTO v_stock_actual, v_Nombre, v_Unidad_Medida, v_Precio
    FROM Producto
    WHERE Codigo_Producto = P_Codigo_Producto;

    IF v_Nombre IS NULL THEN
        SELECT 'Error' AS Estado, 'Producto no encontrado' AS Mensaje;
    ELSE
        IF P_Cantidad <= 0 OR P_Cantidad > v_stock_actual THEN
            SELECT 'Error' AS Estado, CONCAT('Stock insuficiente. Disponible: ', v_stock_actual) AS Mensaje;
        ELSE
            SELECT 'OK' AS Estado, 
                   'Stock validado correctamente' AS Mensaje,
                   v_Nombre AS Nombre,
                   P_Cantidad AS Cantidad,
                   v_Unidad_Medida AS Unidad_Medida,
                   v_Precio AS Precio_Unitario,
                   (v_Precio * P_Cantidad) AS Subtotal;
        END IF;
    END IF;
END //

CREATE PROCEDURE Registrar_DetallesVenta(
    IN P_ID_Venta INT,
    IN P_Codigo_Producto VARCHAR(50),
    IN P_Cantidad INT
)
BEGIN
    DECLARE v_ID_Producto INT;
    DECLARE v_Precio_Unitario DECIMAL(10,2);
    DECLARE v_Subtotal DECIMAL(10,2);
    
    SELECT ID_Producto, Precio INTO v_ID_Producto, v_Precio_Unitario 
    FROM Producto 
    WHERE Codigo_Producto = P_Codigo_Producto;
    
    SET v_Subtotal = v_Precio_Unitario * P_Cantidad;

    START TRANSACTION;
    
    INSERT INTO Detalles_Venta (ID_Venta, ID_Producto, Cantidad, precioUnitario) 
    VALUES (P_ID_Venta, v_ID_Producto, P_Cantidad, v_Precio_Unitario);
    
    UPDATE Producto 
    SET Stock_Tienda = Stock_Tienda - P_Cantidad 
    WHERE ID_Producto = v_ID_Producto;
    
    UPDATE Venta 
    SET Total = Total + v_Subtotal 
    WHERE ID_Venta = P_ID_Venta;
    
    COMMIT;
END //

DELIMITER ;


