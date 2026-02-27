USE shaddaiSystem;
DELIMITER //
CREATE PROCEDURE SP_Generar_Reporte_Ventas(
    IN P_Nombre_Categoria VARCHAR(50),
    IN P_Nombre_Vendedor VARCHAR(300),
    IN P_Fecha_Especifica DATE
)
BEGIN
    DECLARE v_id_Vendedor INT DEFAULT NULL;
    DECLARE v_id_Categoria INT DEFAULT NULL;
    /*Se busca la categoria*/
    IF P_Nombre_Categoria IS NOT NULL THEN
        SELECT ID_Categoria INTO v_id_Categoria 
        FROM Categoria
        WHERE nombre_Categoria = P_Nombre_Categoria;
    END IF;
    /*Se busca al vendedor*/
    IF P_Nombre_Vendedor IS NOT NULL THEN
        SELECT ID_USUARIO INTO v_id_Vendedor
        FROM Usuarios 
        WHERE ID_Rol = 2 
		AND CONCAT(Nombres, ' ', Apellidos) = P_Nombre_Vendedor;
    END IF;
    /*1. Ventas por producto (barras)*/
    SELECT 
        p.Nombre,
        c.nombre_Categoria,
        SUM(dv.Cantidad) AS Total_Vendido,
        SUM(dv.Cantidad * dv.precioUnitario) AS Monto_Total
    FROM Detalles_Venta dv
    INNER JOIN Producto p ON p.ID_Producto = dv.ID_Producto 
    INNER JOIN Categoria c ON c.ID_Categoria = p.ID_Categoria
    INNER JOIN Venta ve ON dv.ID_Venta = ve.ID_Venta 
    INNER JOIN Cliente cl ON ve.ID_Cliente = cl.ID_Cliente
    WHERE (v_id_Categoria IS NULL OR p.ID_Categoria = v_id_Categoria)  
      AND (v_id_Vendedor IS NULL OR ve.ID_Usuario = v_id_Vendedor)
      AND (P_Fecha_Especifica IS NULL OR DATE(ve.Fecha) = P_Fecha_Especifica)
    GROUP BY p.ID_Producto, p.Nombre, c.nombre_Categoria
    ORDER BY Total_Vendido DESC;
    /*2. Ventas por categoría (gráfico circular)*/
    SELECT 
        c.nombre_Categoria,
        SUM(dv.Cantidad * dv.precioUnitario) AS Monto_Total,
        COUNT(DISTINCT ve.ID_Venta) AS Num_Ventas
    FROM Detalles_Venta dv
    INNER JOIN Producto p ON dv.ID_Producto = p.ID_Producto
    INNER JOIN Categoria c ON p.ID_Categoria = c.ID_Categoria
    INNER JOIN Venta ve ON dv.ID_Venta = ve.ID_Venta
    WHERE (v_id_Categoria IS NULL OR p.ID_Categoria = v_id_Categoria) 
      AND (v_id_Vendedor IS NULL OR ve.ID_Usuario = v_id_Vendedor)
      AND (P_Fecha_Especifica IS NULL OR DATE(ve.Fecha) = P_Fecha_Especifica)
    GROUP BY c.ID_Categoria, c.nombre_Categoria;
	/*3. Ventas por vendedor (tabla)*/
    SELECT 
        CONCAT(u.Nombres, ' ', u.Apellidos) AS Vendedor,
        COUNT(DISTINCT ve.ID_Venta) AS Cantidad_Ventas,  
        SUM(dv.Cantidad * dv.precioUnitario) AS Monto_Total 
    FROM Venta ve
    INNER JOIN Usuarios u ON ve.ID_Usuario = u.ID_Usuario
    INNER JOIN Detalles_Venta dv ON ve.ID_Venta = dv.ID_Venta
    INNER JOIN Producto p ON dv.ID_Producto = p.ID_Producto
    WHERE (v_id_Categoria IS NULL OR p.ID_Categoria = v_id_Categoria) 
      AND (v_id_Vendedor IS NULL OR ve.ID_Usuario = v_id_Vendedor)
      AND (P_Fecha_Especifica IS NULL OR DATE(ve.Fecha) = P_Fecha_Especifica)
    GROUP BY u.ID_Usuario, u.Nombres, u.Apellidos
    ORDER BY Monto_Total DESC;
END //
DELIMITER ;
DELIMITER //
CREATE PROCEDURE Obtener_Nombres_Vendedor()
BEGIN
	Select CONCAT(Nombres,' ',Apellidos) AS Vendedor FROM Usuarios WHERE Rol=2;
END //
DELIMITER ;
INSERT INTO Roles(NombreRol) VALUES ('Administrador'),('Vendedor'),('Almacenero')
