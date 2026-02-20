/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.ValidarCantidadAlmacenDTO;
import Modelo.DTO.ValidarCantidadTiendaDTO;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



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
    public boolean RegistrarDetalleTransferencia(int idTransferencia,int idProducto,int Cantidad){
        String sql="{CALL Registrar_Detalle_Transferencia(?,?,?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setInt(1,idTransferencia);
            stmt.setInt(2,idProducto);
            stmt.setInt(3, Cantidad);
            stmt.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public ValidarCantidadTiendaDTO ValidarCantidadProductoTienda(String nombreProducto,int Cantidad){
        /*Aún falta*/
      String sql="{CALL Validar_cantidad_y_producto(?,?)}";
      try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
          stmt.setString(1,nombreProducto);
          stmt.setInt(2,Cantidad);
          ResultSet rs=stmt.executeQuery();
          if(rs.next()){
              String estado=rs.getString("Estado");
              String mensaje=rs.getString("Mensaje");
              if("OK".equals(estado)){
                  return new ValidarCantidadTiendaDTO(true,mensaje,rs.getString("Nombre"),rs.getInt("Cantidad"),
                  rs.getDouble("Precio_Unitario"),rs.getDouble("Subtotal"),rs.getInt("Stock_Disponible"));
              }else{
                  return new ValidarCantidadTiendaDTO(mensaje);
              }
          }
      }catch(SQLException e){
          e.printStackTrace();
           return new ValidarCantidadTiendaDTO(
                "Error en BD: " + e.getMessage()
            );
       }
      return new ValidarCantidadTiendaDTO("Error en obtener respuesta del servidor");
    }
    public ValidarCantidadAlmacenDTO ValidarCantidadProductoAlmacen(String nombreProducto,int Cantidad){
        String sql="{CALL  Validar_Cantidad_Trasladar(?,?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setString(1, nombreProducto);
            stmt.setInt(2,Cantidad);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                String estado=rs.getString("Estado");
                String mensaje=rs.getString("Mensaje");
                if("OK".equals(estado)){
                return new ValidarCantidadAlmacenDTO(true,mensaje,rs.getString("Nombre"),rs.getInt("Cantidad"),rs.getString("Unidad de Medida"));
                }else{
                    return new ValidarCantidadAlmacenDTO(mensaje);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            return new ValidarCantidadAlmacenDTO("Error en BD: " + e.getMessage());
        }
        return new ValidarCantidadAlmacenDTO("Error en obtener respuesta del servidor");
    }
    public List<String> ObtenerNombresProductoVenta(){
        String sql="{CALL Obtener_Nombres_Producto()}";
        List<String> nombresVenta=new ArrayList<>();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                nombresVenta.add(rs.getString("Nombres"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en BD: "+e.getMessage());
        }
        return nombresVenta;
    }
    public List<String> ObtenerNombresProductoTrasladar(){
        String sql="{CALL Obtener_Nombres_ProductoTraslado()}";
         List<String> nombresAlmacen=new ArrayList<>();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                nombresAlmacen.add(rs.getString("Nombres"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en BD: "+e.getMessage());
        }
        return nombresAlmacen;
    }
      public List<String> ObtenerNombresProducto(){
        String sql="Select Nombre from Producto";
        List<String> nombresP=new ArrayList<>();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                nombresP.add(rs.getString("Nombre"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en BD: "+e.getMessage());
        }
        return nombresP;
    }
}
