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


DELIMITER //
DROP PROCEDURE IF EXISTS Registrar_Venta //

CREATE PROCEDURE Registrar_Venta(
    IN P_Tipo_Venta ENUM('Tienda', 'Whatsapp'),
    IN P_ID_Usuario INT,
    IN P_Tipo_Cliente ENUM('Persona', 'Empresa'),
    IN P_DNI_Cliente VARCHAR(8),
    IN P_RUC_Cliente VARCHAR(11),
    IN P_Nombre_Cliente VARCHAR(300), 
    IN P_Tipo_Comprobante ENUM('Boleta', 'Factura'),
    IN P_Metodo_Pago ENUM('Yape/Plin', 'Efectivo')
)
BEGIN
    DECLARE v_ID_Cliente INT DEFAULT NULL;
    DECLARE v_ID_Venta INT;

    START TRANSACTION;

    IF P_Tipo_Cliente = 'Persona' AND P_DNI_Cliente IS NOT NULL AND P_DNI_Cliente != '' THEN
        SELECT ID_Cliente INTO v_ID_Cliente FROM Cliente WHERE DNI = P_DNI_Cliente LIMIT 1;
        
        IF v_ID_Cliente IS NULL THEN
            INSERT INTO Cliente (DNI, NombreCompleto, Tipo_Cliente, Estado) 
            VALUES (P_DNI_Cliente, IFNULL(P_Nombre_Cliente, 'Cliente Sin Nombre'), 'Persona', 'No Frecuente');
            SET v_ID_Cliente = LAST_INSERT_ID();
        END IF;
        
    ELSEIF P_Tipo_Cliente = 'Empresa' AND P_RUC_Cliente IS NOT NULL AND P_RUC_Cliente != '' THEN
        SELECT ID_Cliente INTO v_ID_Cliente FROM Cliente WHERE RUC = P_RUC_Cliente LIMIT 1;
        
        IF v_ID_Cliente IS NULL THEN
            INSERT INTO Cliente (RUC, NombreCompleto, Tipo_Cliente, Estado) 
            VALUES (P_RUC_Cliente, IFNULL(P_Nombre_Cliente, 'Empresa Sin Nombre'), 'Empresa', 'No Frecuente');
            SET v_ID_Cliente = LAST_INSERT_ID();
        END IF;
    END IF;

    INSERT INTO Venta (ID_Usuario, ID_Cliente, Fecha, Total, Tipo_Comprobante, Tipo_Venta, Metodo_Pago) 
    VALUES (P_ID_Usuario, v_ID_Cliente, NOW(), 0.00, P_Tipo_Comprobante, P_Tipo_Venta, P_Metodo_Pago);
    
    SET v_ID_Venta = LAST_INSERT_ID();
    
    COMMIT;
    SELECT 'OK' AS Estado, 'Venta registrada' AS Mensaje, v_ID_Venta AS ID_Venta_Generado;
END //
DELIMITER ;
