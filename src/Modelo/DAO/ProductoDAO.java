/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.sql.SQLException;



/**
 *
 * @author Usuario
 */
public class ProductoDAO {
    public int RegistrarTransferencia(int idUsuario){
        String sql="{CALL Registrar_Transferencia (?)}";
        int idTransferencia=0;
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setInt(1,idUsuario);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                idTransferencia=rs.getInt("P_ID_GENERADO");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
        return idTransferencia;
    }
    public void RegistrarDetalleTransferencia(int idTransferencia,int idProducto,int Cantidad){
        String sql="{CALL Registrar_Detalle_Transferencia(?,?,?)}";
    }
    public void ValidarCantidadProductoTienda(String codigoProducto,int Cantidad){
      String sql="{CALL Validar_cantidad_y_producto(?,?)}";
    }
    public void ValidarCantidadProductoAlmacen(String codigoProducto,int Cantidad){
        String sql="{CALL  Validar_Cantidad_Trasladar(?,?)}";
    }
    public void ObtenerCodigoProductoVenta(){
        String sql="{CALL Obtener_Codigos_Producto()}";
    }
    public void ObtenerCodigoProductoTrasladar(){
        String sql="{CALL Obtener_Codigos_ProductoTraslado()}";
    }
}
