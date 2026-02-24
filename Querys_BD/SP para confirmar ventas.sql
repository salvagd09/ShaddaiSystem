USE shaddaiSystem;
/*SP para marcar un registrar un pedido como Pendiente*/
DELIMITER //
CREATE PROCEDURE SP_Pedido_Pendiente(IN P_Tipo_Pedido ENUM("Tienda","Whatsapp"),IN P_Tipo_Cliente ENUM("Persona","Empresa"),
IN P_DNI VARCHAR(9),IN P_RUC VARCHAR(11),IN P_NombreCompleto VARCHAR(300))
BEGIN
	DECLARE v_ID_Cliente INT;
    DECLARE v_ID_Pedido INT;
    START TRANSACTION;
    IF P_Tipo_Pedido="Tienda" THEN
		ROLLBACK;
			Select 'Error' AS Estado, 'Solo los pedidos por Whatsapp pueden ser Pendientes' as Mensaje,
			NULL AS ID_Pedido_Generado;
	ELSE
		IF P_Tipo_Cliente="Persona" THEN
			Select ID_Cliente INTO v_ID_Cliente
			FROM Cliente WHERE DNI=P_DNI;
		ELSE
			Select ID_Cliente INTO v_ID_Cliente
			FROM Cliente WHERE RUC=P_RUC;
		END IF;
		  IF v_ID_Cliente IS NULL THEN
			INSERT INTO Cliente(DNI, NombreCompleto, Estado, RUC, Tipo_Cliente) 
			VALUES (P_DNI, P_NombreCompleto, 'No_Frecuente', P_RUC, P_Tipo_Cliente);
			SET V_ID_Cliente = LAST_INSERT_ID();
		  END IF;
		  INSERT INTO Pedido(ID_Cliente,Estado,Fecha) VALUES(v_ID_Cliente,"Pendiente",NOW());
		  SET v_ID_Pedido= LAST_INSERT_ID();
          COMMIT;
		  Select 'OK' as Estado,'Pedido registrado como Pendiente' AS Mensaje,v_ID_Pedido AS ID_Pedido_Generado;
	END IF;
END //
DELIMITER ;
DELIMITER //
/*SP para registrar los detalles de los pedidos marcados como pendiente*/
CREATE PROCEDURE SP_Pedido_Pendiente_Detalles(IN P_ID_Pedido_Generado INT,IN P_Nombre_Producto VARCHAR(50),IN P_Cantidad INT)
BEGIN
	DECLARE v_ID_Producto INT;
    DECLARE v_Precio_Unitario DECIMAL(10,2);
    Select ID_Producto,Precio INTO v_ID_Producto,v_Precio_Unitario from Producto
    WHERE Nombre=P_Nombre_Producto;
    INSERT INTO Detalles_Pedido(ID_Pedido,ID_Producto,cantidad,precioUnitario) VALUES(P_ID_Pedido_Generado,v_ID_Producto,P_Cantidad,
    v_Precio_Unitario);
END //
DELIMITER ; 
DELIMITER //
/*Sirve para poder insertar IDs de pedidos pendientes del cliente al que le pertenece el DNI colocado*/
CREATE PROCEDURE Obtener_ID_Pedidos_Pendiente(IN P_DNI VARCHAR(8))
BEGIN
	DECLARE v_ID_Cliente INT;
    Select ID_Cliente INTO v_ID_Cliente FROM Cliente
    WHERE DNI=P_DNI;
    START TRANSACTION;
    IF v_ID_Cliente IS NULL THEN
		Select 'Error' AS Estado, 'Este cliente no tiene ningún pedido' AS Mensaje;
	ELSE
		Select ID_Pedido FROM Pedido WHERE ID_Cliente=v_ID_Cliente AND Estado="Pendiente";
        COMMIT;
        Select 'OK' AS Estado, 'Pedidos pendientes de este cliente' AS Mensaje;
	END IF;
END //
DELIMITER ;
DELIMITER //
/*Sirve para ver los datos del cliente del pedido que se quiere confirmar*/
CREATE PROCEDURE Obtener_DatosP_Pedido_Pendiente(IN P_ID_Pedido INT)
BEGIN
	Select cl.NombreCompleto,Pe.Fecha FROM Cliente cl JOIN Pedido Pe ON cl.ID_Cliente=Pe.ID_Cliente
    WHERE pe.ID_Pedido=P_ID_Pedido AND pe.Estado="Pendiente";
END //
DELIMITER ;
DELIMITER //
/*Sirve para poder ver la lista de productos del pedido que se quiere confirmar*/
CREATE PROCEDURE Obtener_DatosDP_Pedido_Pendiente(IN P_ID_Pedido INT)
BEGIN
	Select pro.Nombre,dpe.cantidad,pro.Stock_Tienda,IF(pro.Stock_Tienda>=dpe.cantidad,'Si','No')AS Disponible FROM Producto pro
    JOIN Detalles_Pedido dpe ON pro.ID_Producto=dpe.ID_Producto
    JOIN Pedido pe ON pe.ID_Pedido=dpe.ID_Pedido
    WHERE dpe.ID_Pedido=P_ID_Pedido AND pe.Estado="Pendiente";
END //
DELIMITER ;
DELIMITER //
/*Sirve para confirmar el pedido. Jhossel,tienes que complementar con programación en Java para que no se 
permita confirmar ventas si hay productos que tienen el campo de disponible como No dentro de la tabla
que muestra los productos.*/
CREATE PROCEDURE Confirmar_Pedido(IN P_ID_Pedido INT)
BEGIN
	UPDATE Pedido SET Estado="Confirmado" WHERE ID_Pedido=P_ID_Pedido;
END //
DELIMITER ;
DELIMITER //
/*Para actualizar un producto que tiene el campo de disponible como No. Se tiene 
que complementar con programación en Java para que al seleccionar un producto en esa lista
de la interfaz, se obtenga el nombre del producto*/
CREATE PROCEDURE Actualizar_DP_Pedido_Pendiente(IN P_ID_Pedido INT,IN P_NombreProducto VARCHAR(200),IN P_CantidadNueva INT)
BEGIN
	DECLARE v_ID_Producto INT;
    DECLARE v_stockTienda INT;
    Select ID_Producto,Stock_Tienda into v_ID_Producto,v_stockTienda FROM Producto
    WHERE Nombre=P_NombreProducto;
    START TRANSACTION;
    IF v_stockTienda<P_CantidadNueva THEN
		Select 'Error' as Estado,'Esa cantidad no es valida para la actualizacion' AS Mensaje;
	ELSE
		UPDATE Detalles_Pedido SET cantidad=P_CantidadNueva WHERE ID_Pedido=P_ID_Pedido AND ID_Producto=v_ID_Producto;
        COMMIT;
        Select 'OK' as Estado,'Cantidad actualizada correctamente' as Mensaje;
	END IF;
END //
DELIMITER ;
