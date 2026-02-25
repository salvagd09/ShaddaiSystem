/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.ValidarCantidadAlmacenDTO;
import Modelo.DTO.ValidarCantidadTiendaDTO;
import Modelo.Entidad.Producto;
import java.sql.CallableStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Usuario
 */
public class ProductoDAO {
       public ValidarCantidadTiendaDTO ValidarCantidadProductoTienda(String nombreProducto,int Cantidad){
      String sql="{CALL Validar_cantidad_y_producto(?,?)}";
      try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = conn.prepareCall(sql)){
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
        CallableStatement stmt =  conn.prepareCall(sql)){
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
        CallableStatement stmt = conn.prepareCall(sql)){
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
        CallableStatement stmt =  conn.prepareCall(sql)){
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
        CallableStatement stmt =  conn.prepareCall(sql)){
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
    
    // CU01-Registrar Venta  
    // Buscar producto por nombre para registrar venta
    public Producto buscarPorNombre(String nombreProducto) {
        Producto prod = null;
        
        String sql = "SELECT ID_Producto, Codigo_Producto, Nombre, Precio, Stock_Tienda " +
                     "FROM Producto WHERE Nombre LIKE ? LIMIT 1";
                     
        try (Connection conn = new dbConexion().conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, "%" + nombreProducto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    prod = new Producto();
                    prod.setIDProducto(rs.getInt("ID_Producto"));
                    prod.setCodigoProducto(rs.getString("Codigo_Producto"));
                    prod.setNombre(rs.getString("Nombre"));
                    prod.setPrecio(rs.getDouble("Precio"));
                    prod.setStockTienda(rs.getInt("Stock_Tienda"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el producto: " + e.getMessage(), e);
        }
        return prod;
    }
}
