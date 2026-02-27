USE shaddaiSystem;
DELIMITER //
DELIMITER ;
Select * from Venta;
/*Mostrar los datos del cliente del pedido recién confirmado*/
DELIMITER //
CREATE PROCEDURE Obtener_DatosP_Pedido_Confirmado(IN P_ID_Pedido INT)
BEGIN
	Select NombreCompleto,DNI,RUC FROM Cliente cl JOIN Pedido pe ON cl.ID_Cliente=pe.ID_Cliente WHERE pe.ID_Pedido=P_ID_Pedido AND pe.Estado="Confirmado";
END //
DELIMITER ;
DELIMITER //
/*Mostrar los productos del pedido confirmado en la lista para Registrar Ventas*/
CREATE PROCEDURE Obtener_DatosDP_Pedido_Confirmado(IN P_ID_Pedido INT)
BEGIN
	Select pro.Codigo_Producto,pro.Nombre,dpe.cantidad,pro.Precio AS 'Precio',(pro.Precio*dpe.cantidad) AS Subtotal FROM Producto pro
    JOIN Detalles_Pedido dpe ON pro.ID_Producto=dpe.ID_Producto
    JOIN Pedido pe ON pe.ID_Pedido=dpe.ID_Pedido
    WHERE dpe.ID_Pedido=P_ID_Pedido AND pe.Estado="Confirmado";
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE Registrar_Venta_General(
    IN P_ID_Pedido INT,        
    IN P_ID_Usuario INT,       
    IN P_DNI VARCHAR(9),
    IN P_RUC VARCHAR(11),
    IN P_Total DECIMAL(10,2),
    IN P_NombreCompleto VARCHAR(300),
    IN P_Tipo_Cliente ENUM('Persona','Empresa'),
    IN P_Tipo_Venta ENUM('Tienda', 'Whatsapp'),
    IN P_Tipo_Comprobante ENUM('Boleta', 'Factura'),
    IN P_Metodo_Pago ENUM('Yape/Plin', 'Efectivo')
)
BEGIN
    DECLARE v_id_cliente INT DEFAULT NULL;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SELECT 'Error en la transacción' AS Mensaje;
    END;
    IF P_Tipo_Cliente = 'Persona' THEN
        SELECT ID_Cliente INTO v_id_cliente 
        FROM Cliente 
        WHERE DNI = P_DNI 
        LIMIT 1;
    ELSE
        SELECT ID_Cliente INTO v_id_cliente 
        FROM Cliente 
        WHERE RUC = P_RUC 
        LIMIT 1;
    END IF;
    IF v_id_cliente IS NULL THEN
        INSERT INTO Cliente(DNI, NombreCompleto, Estado, RUC, Tipo_Cliente) 
        VALUES (P_DNI, P_NombreCompleto, 'No_Frecuente', P_RUC, P_Tipo_Cliente);
        SET v_id_cliente = LAST_INSERT_ID();
    END IF;
    INSERT INTO Venta(ID_Pedido, ID_Usuario, ID_Cliente, Fecha, Total, Tipo_Comprobante, Tipo_Venta, Metodo_Pago)
    VALUES (P_ID_Pedido, P_ID_Usuario, v_id_cliente, NOW(), P_Total, P_Tipo_Comprobante, P_Tipo_Venta, P_Metodo_Pago);
    IF P_ID_Pedido IS NOT NULL THEN
        UPDATE Pedido SET Estado = 'Finalizado' WHERE ID_Pedido = P_ID_Pedido;
    END IF;
    SELECT LAST_INSERT_ID() AS ID_Venta_Generada;
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE Registrar_Detalle_Venta(IN P_ID_Venta_Generada INT,IN P_Nombre_Producto VARCHAR(200), IN P_Cantidad INT)
BEGIN
	DECLARE v_id_Producto INT;
    DECLARE v_precioUnitario DECIMAL(10,2);
	DECLARE v_stock_actualTienda INT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SELECT 'Error en la transacción' AS Mensaje;
    END;
	SELECT ID_Producto,Precio,Stock_Tienda
    INTO v_id_Producto, v_precioUnitario, v_stock_actualTienda
    FROM Producto
    WHERE Nombre = P_Nombre_Producto;
    INSERT INTO Detalles_Venta(ID_Venta,ID_Producto,Cantidad,PrecioUnitario) VALUES(P_ID_Venta_Generada,v_id_Producto,P_cantidad,v_precioUnitario);
	UPDATE Producto 
    SET Stock = Stock - P_Cantidad 
    WHERE ID_Producto = v_id_Producto;
    SELECT 'Detalle registrado correctamente' AS Mensaje;
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE Validar_cantidad_y_producto(IN P_Nombre_Producto VARCHAR(200), IN P_Cantidad INT)
BEGIN
	DECLARE v_stock_actualTienda INT;
    DECLARE v_nombre VARCHAR(255);
    DECLARE v_precio DECIMAL(10,2);
    SELECT Stock_Tienda, Nombre, Precio 
    INTO v_stock_actualTienda, v_nombre, v_precio
    FROM Producto
    WHERE Nombre = P_Nombre_Producto;
        IF v_stock_actualTienda < P_Cantidad THEN
        SELECT 
            'ERROR' AS Estado, 
            CONCAT('Stock insuficiente. Disponible: ', v_stock_actualTienda) AS Mensaje;
    ELSE
        SELECT 
            'OK' AS Estado, 
            'Producto agregado correctamente' AS Mensaje,
            v_nombre AS Nombre,
            P_Cantidad AS Cantidad,
            v_precio AS Precio_Unitario,
            (P_Cantidad * v_precio) AS Subtotal, 
            v_stock_actualTienda AS Stock_Disponible;
    END IF;
END //
DELIMITER ;
DELIMITER //
/*Sirve para que el usuario pueda seleccionar un producto en el ComboBox*/
CREATE PROCEDURE Obtener_Nombres_Producto()
BEGIN
	Select Nombre FROM Productos
    WHERE Stock_Tienda>0
    ORDER BY Nombre;
END//
DELIMITER ;
DELIMITER //
CREATE PROCEDURE Mostrar_Datos_Cliente(
    IN P_Tipo_Cliente VARCHAR(20),
    IN P_DNI VARCHAR(8)
)
BEGIN
    DECLARE v_ID_Cliente INT DEFAULT NULL;
    DECLARE v_RUC VARCHAR(11);
    DECLARE v_NombreCompleto VARCHAR(300);
    SELECT ID_Cliente, RUC, NombreCompleto 
    INTO v_ID_Cliente, v_RUC, v_NombreCompleto 
    FROM Cliente 
    WHERE Tipo_Cliente = P_Tipo_Cliente 
      AND DNI = P_DNI;
    IF v_ID_Cliente IS NULL THEN
        SELECT 'Error' AS Estado, 
               'Cliente no existe' AS Mensaje,
               NULL AS RUC,
               NULL AS NombreCompleto;
    ELSE
        SELECT 'OK' AS Estado,
               CONCAT('Cliente ', v_NombreCompleto, ' encontrado') AS Mensaje,
               IFNULL(v_RUC, '') AS RUC, 
               v_NombreCompleto AS NombreCompleto;
    END IF;
END //
DELIMITER ;