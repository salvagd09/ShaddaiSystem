CREATE DATABASE shaddaiSystem;
USE shaddaiSystem;
CREATE TABLE Roles(ID_ROL INT PRIMARY KEY AUTO_INCREMENT, NombreRol VARCHAR(20));
CREATE TABLE Usuarios
(ID_USUARIO INT PRIMARY KEY AUTO_INCREMENT,Nombres varchar(150),
Apellidos VARCHAR(150),Contrasena VARCHAR(100), Rol INT ,FOREIGN KEY (Rol) references Roles(ID_ROL));
CREATE TABLE Cliente(ID_Cliente INT PRIMARY KEY AUTO_INCREMENT, DNI VARCHAR(8),NombreCompleto VARCHAR(300), Telefono VARCHAR(9), 
Direccion VARCHAR(300),Correo VARCHAR(100), Estado ENUM("Frecuente","No Frecuente") DEFAULT "No Frecuente");
CREATE TABLE Producto(ID_Producto INT PRIMARY KEY AUTO_INCREMENT, Codigo_Producto VARCHAR(50), Nombre VARCHAR(200),Stock_Tienda INT,
Stock_Almacen INT,Unidad_Medida VARCHAR(50),Precio DECIMAL(10,2), Stock_Minimo INT);
CREATE TABLE Pedido(ID_Pedido INT PRIMARY KEY AUTO_INCREMENT,ID_Cliente INT, Estado ENUM("Pendiente","Confirmado"),Fecha DATETIME,
FOREIGN KEY (ID_Cliente) REFERENCES Cliente(ID_Cliente));
CREATE TABLE Detalles_Pedido(ID_Detalle_Pedido INT PRIMARY KEY AUTO_INCREMENT,ID_Pedido INT,ID_Producto INT,cantidad INT,
precioUnitario DECIMAL(10,2),FOREIGN KEY (ID_Pedido) REFERENCES Pedido(ID_Pedido), FOREIGN KEY (ID_Producto) REFERENCES Producto(ID_Producto));
CREATE TABLE Venta(ID_Venta INT PRIMARY KEY AUTO_INCREMENT,ID_Pedido INT NULL,ID_Usuario INT,ID_Cliente INT,Fecha DATETIME,
Total DECIMAL(10,2),Tipo_Comprobante VARCHAR(20),FOREIGN KEY (ID_Pedido) REFERENCES Pedido(ID_Pedido),
FOREIGN KEY(ID_Usuario) REFERENCES Usuarios(ID_USUARIO), FOREIGN KEY(ID_Cliente) REFERENCES Cliente(ID_Cliente));
CREATE TABLE Detalles_Venta(ID_Detalle_Venta INT PRIMARY KEY AUTO_INCREMENT, ID_Venta INT, ID_Producto INT, Cantidad INT, precioUnitario DECIMAL(10,2),
FOREIGN KEY (ID_Venta) REFERENCES Venta(ID_Venta), FOREIGN KEY (ID_Producto) REFERENCES Producto(ID_Producto));
CREATE TABLE TransferenciaATienda(ID_Transferencia INT PRIMARY KEY AUTO_INCREMENT,ID_Usuario INT,Fecha DATETIME,
FOREIGN KEY (ID_Usuario) REFERENCES Usuarios(ID_USUARIO));
CREATE TABLE Detalles_TransferenciaTienda(ID_Detalles_Trans INT PRIMARY KEY AUTO_INCREMENT,ID_Transferencia INT,ID_Producto INT,Cantidad INT, 
FOREIGN KEY(ID_Transferencia) REFERENCES TransferenciaATienda(ID_Transferencia),FOREIGN KEY(ID_Producto) REFERENCES Producto(ID_Producto));
CREATE TABLE Categoria(ID_Categoria INT PRIMARY KEY AUTO_INCREMENT,nombre_Categoria VARCHAR(50));
CREATE TABLE Detalles_Notificacion(ID_Detalle_Notificacion INT PRIMARY KEY AUTO_INCREMENT,ID_Notificacion INT,ID_Producto INT,
Stock_Actual INT,FOREIGN KEY (ID_Notificacion) REFERENCES Notificacion(ID_Notificacion), 
FOREIGN KEY (ID_Producto) REFERENCES Producto(ID_Producto));
/*Alteraciones a las Tablas originales*/
ALTER TABLE Producto ADD COLUMN ID_Categoria INT; 
ALTER TABLE Producto ADD CONSTRAINT FOREIGN KEY (ID_Categoria) REFERENCES Categoria(ID_Categoria);
CREATE TABLE Notificacion(ID_Notificacion INT PRIMARY KEY AUTO_INCREMENT,Titulo VARCHAR(255),Mensaje VARCHAR(255),
Fecha DATE,Estado ENUM ("Leido","No Leido"));
ALTER TABLE Cliente ADD COLUMN RUC VARCHAR(11) NULL;
ALTER TABLE Producto ADD CONSTRAINT UNIQUE(Codigo_Producto);
ALTER TABLE Venta ADD COLUMN Tipo_Venta ENUM("Tienda","Whatsapp");
ALTER TABLE Pedido MODIFY Estado ENUM("Pendiente","Confirmado","Finalizado");
ALTER TABLE Venta ADD COLUMN Metodo_Pago ENUM("Yape/Plin","Efectivo");
ALTER TABLE Cliente ADD COLUMN Tipo_Cliente ENUM("Persona","Empresa");
ALTER TABLE Usuarios ADD COLUMN username VARCHAR(200) UNIQUE;
ALTER TABLE Usuarios ADD CONSTRAINT UNIQUE(Contrasena);
ALTER TABLE Venta MODIFY Tipo_Comprobante enum("Boleta","Factura");
/*Procedimientos para registrar una transferencia de tipo CU05 así como sus detalles*/
DELIMITER //
CREATE PROCEDURE Registrar_Transferencia 
(IN P_ID_USUARIO INT, OUT P_ID_GENERADO INT)
BEGIN
INSERT INTO TransferenciaATienda(ID_Usuario,Fecha) VALUES(P_ID_USUARIO,NOW());
SET P_ID_GENERADO=LAST_INSERT_ID();
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE Registrar_Detalle_Transferencia(IN P_ID_Transferencia INT,IN P_ID_Producto INT,IN P_Cantidad INT)
BEGIN
	INSERT INTO Detalles_TransferenciaTienda(ID_Transferencia,ID_Producto,Cantidad) VALUES (P_ID_Transferencia,P_ID_Producto,P_Cantidad);
    UPDATE Producto
    SET Stock_Tienda=Stock_Tienda+P_Cantidad,
	Stock_Almacen=Stock_Almacen-P_Cantidad
    WHERE ID_Producto=P_ID_Producto;
END //
DELIMITER ;
DELIMITER //
/*Procedimiento almacenado para validar cantidad a trasladar y poder agregar datos en la lista 
de productos a trasladar*/
CREATE PROCEDURE Validar_Cantidad_Trasladar(
    IN P_Nombre_Producto VARCHAR(200),
    IN P_Cantidad INT
)
BEGIN
    DECLARE v_stock_actualAlmacen INT;
    DECLARE v_Nombre VARCHAR(200);
    DECLARE v_Unidad_Medida VARCHAR(50);
    SELECT Stock_Almacen,Nombre,Unidad_Medida INTO v_stock_actualAlmacen,v_Nombre,v_Unidad_Medida 
    FROM Producto
    WHERE Nombre = P_Nombre_Producto;
    IF P_Cantidad <= 0 OR P_Cantidad > v_stock_actualAlmacen THEN
		SELECT 'Error' AS Estado,
         CONCAT('Stock insuficiente. Disponible: ', v_stock_actualAlmacen) AS Mensaje;
    ELSE
        SELECT 'OK' AS Estado,
        'Cantidad valida para traslado' AS Mensaje,
        v_Nombre as Nombre,
        P_Cantidad as Cantidad,
        v_Unidad_Medida as `Unidad de Medida`;
    END IF;
END //
DELIMITER ;
DELIMITER //
/*Mostrar productos a trasladar*/
CREATE PROCEDURE Obtener_Nombres_ProductoTraslado()
BEGIN
	Select Nombre FROM Productos
    WHERE Stock_Almacen>0
    ORDER BY Nombre;
END//
DELIMITER ;
DELIMITER //
/*Procedimientos almacenados para realizar el CU04*/
CREATE PROCEDURE Registrar_Cliente_Frecuente(IN P_DNI VARCHAR(8),IN P_RUC VARCHAR(11),IN P_Telefono VARCHAR(9), 
IN P_Correo VARCHAR(100),IN P_Direccion VARCHAR(300))
BEGIN
	UPDATE Cliente
    SET Telefono=P_Telefono,Correo=P_Correo,Direccion=P_Direccion,Estado="Frecuente"
    WHERE (DNI IS NOT NULL AND DNI=P_DNI) OR (RUC IS NOT NULL AND RUC=P_RUC);
END //
DELIMITER ;
DELIMITER //
/*Además de contabilizar también muestra el historial de compras.Es útil también en la notificación que 
afirma si un cliente ya existe en CU01*/
CREATE PROCEDURE Contabilizar_Compras_Mes(IN P_DNI VARCHAR(8),IN P_RUC VARCHAR(11))
BEGIN
	Select c.NombreCompleto AS Nombre_Cliente,
    v.ID_Venta as ID_Venta,
    v.Fecha as Fecha,
    (SELECT COUNT(*) FROM Venta v2 WHERE v2.ID_Cliente=c.ID_Cliente 
    AND MONTH(v2.Fecha)=MONTH(CURDATE())
    AND YEAR(v2.Fecha)=YEAR(CURDATE())) AS Total_Compras_Mes
    FROM Venta v
    JOIN Cliente c ON v.ID_Cliente=c.ID_Cliente
    WHERE ((P_DNI IS NOT NULL AND c.DNI = P_DNI) OR (P_RUC IS NOT NULL AND c.RUC = P_RUC))
    AND MONTH(v.Fecha)=MONTH(CURDATE())
    AND YEAR(v.Fecha)=YEAR(CURDATE());
END //
DELIMITER ;
