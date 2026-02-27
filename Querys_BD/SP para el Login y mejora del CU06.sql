USE shaddaiSystem;
DELIMITER // 
CREATE PROCEDURE Login_Rol(IN P_username VARCHAR(200),IN P_contrasena VARCHAR(100))
BEGIN
	Select ID_USUARIO,NombreRol from Usuarios u
    INNER JOIN Roles r ON Rol=ID_ROL 
    Where username=P_username AND Contrasena=P_contrasena;
END //
DELIMITER ;
/*Trigger para el CU06*/
DELIMITER //
/*Para insertar notificaciones*/
CREATE TRIGGER Notificacion_Bajo_Stock
AFTER UPDATE ON Producto
FOR EACH ROW
BEGIN
	DECLARE var_id_tienda INT DEFAULT NULL;
    DECLARE var_id_almacen INT DEFAULT NULL;
    IF new.Stock_Tienda<new.Stock_Minimo THEN
		Select ID_Notificacion INTO var_id_tienda
		FROM Notificacion
		Where Fecha=CURDATE() And Titulo='Alerta de bajo stock en tienda'
		LIMIT 1;
		IF var_id_tienda IS NULL THEN 
			INSERT INTO Notificacion(Titulo,Mensaje,Fecha,Estado) VALUES('Alerta de bajo stock en tienda',
			'Productos que alcanzaron o son inferiores al stock mínimo en tienda',CURDATE(),"No Leido");
			SET var_id_tienda=LAST_INSERT_ID();
        END IF;
        INSERT INTO Detalles_Notificacion(ID_Notificacion,ID_Producto,Stock_Actual) VALUES
        (var_id_tienda,NEW.ID_Producto,NEW.Stock_Tienda);
	END IF;
    IF new.Stock_Almacen<new.Stock_Minimo THEN
    Select ID_Notificacion INTO var_id_almacen
		FROM Notificacion
		Where Fecha=CURDATE() And Titulo='Alerta de bajo stock en almacen'
		LIMIT 1;
		IF var_id_almacen IS NULL THEN 
			INSERT INTO Notificacion(Titulo,Mensaje,Fecha,Estado) VALUES('Alerta de bajo stock en almacen',
			'Productos que alcanzaron o son inferiores el stock mínimo',CURDATE(),"No Leido");
			SET var_id_almacen=LAST_INSERT_ID();
        END IF;
        INSERT INTO Detalles_Notificacion(ID_Notificacion,ID_Producto,Stock_Actual) VALUES
        (var_id_almacen,NEW.ID_Producto,NEW.Stock_Almacen);
	END IF;
END //
DELIMITER ;
/*Muestra las notifiaciones al ingresar al área de Notificaciones en Java*/
DELIMITER //
CREATE PROCEDURE Mostrar_Notificaciones()
BEGIN
	Select ID_Notificacion,Titulo,Mensaje,Fecha FROM Notificacion WHERE Estado="No Leido";
END //
DELIMITER ;
DELIMITER //
/*Muestra los productos con bajo stock al hacer click en la notificación en Java*/
CREATE PROCEDURE Mostrar_Lista_ProductosBajoStock(IN P_ID_Notificacion INT)
BEGIN
		Select dn.Titulo,p.Nombre, dn.Stock_Actual,p.Stock_Minimo from Producto p
        INNER JOIN Detalles_Notificacion dn ON dn.ID_Producto=p.ID_Producto 
        INNER JOIN Notifcacion n ON n.ID_Notificacion=dn.ID_Notificacion
        WHERE dn.ID_Notificacion=P_ID_Notificacion AND dn.Stock_Actual<p.Stock_Minimo;
END //
DELIMITER ;
/*Se realiza esto al cerrar la lista de productos con bajo stock*/
DELIMITER //
CREATE PROCEDURE Notificacion_Leida(IN P_ID_Notificacion INT)
BEGIN
	UPDATE Notificacion SET Estado="Leido" WHERE ID_Notificacion=P_ID_Notificacion;
END //
DELIMITER ;

