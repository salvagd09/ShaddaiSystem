/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.NotificacionDTO;
import Modelo.DTO.ProductosBajoStockDTO;
import java.util.List;
import java.sql.*;
import java.sql.CallableStatement;
import java.util.ArrayList;


/**
 *
 * @author Usuario
 */
public class NotificacionDAO {
    public List<NotificacionDTO> MostrarNotificaciones(){
        String sql="{CALL Mostrar_Notificaciones()}";
        List<NotificacionDTO> notificaciones=new ArrayList<>();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = conn.prepareCall(sql)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                int idNotificacion=rs.getInt("ID_Notificacion");
                String Titulo=rs.getString("Titulo");
                String Mensaje=rs.getString("Mensaje");
                Date Fecha=rs.getDate("Fecha");
                NotificacionDTO notificacion=new NotificacionDTO(idNotificacion,Titulo,Mensaje,Fecha);
                notificaciones.add(notificacion);
            }
            if(notificaciones.isEmpty()){
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en la base de datos:"+e.getMessage());
        }
        return notificaciones;
    }
    public List<ProductosBajoStockDTO> MostrarListaProductosStockBajo(int IDNotificacion){
        String sql="CALL {Mostrar_Lista_ProductosBajoStock(IN P_ID_Notificacion INT)}";
        List<ProductosBajoStockDTO> productosBajos=new ArrayList<>();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = conn.prepareCall(sql)){
            stmt.setInt(1, IDNotificacion);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                int Stock_Actual=rs.getInt("Stock_Actual");
                String Titulo=rs.getString("Titulo");
                String Nombre=rs.getString("Nombre");
                int Stock_Minimo=rs.getInt("Stock_Minimo");
                ProductosBajoStockDTO productoBajo=new ProductosBajoStockDTO(Titulo,Nombre,Stock_Actual,Stock_Minimo);
                productosBajos.add(productoBajo);
            }
            if(productosBajos.isEmpty()){
                return null;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en la base de datos:"+e.getMessage());
        }
        return productosBajos;
    }
    public boolean NotificacionLeida(int IDNotificacion){
       String sql="CALL {Notificacion_Leida(?)}";
       try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = conn.prepareCall(sql)){
           stmt.setInt(1, IDNotificacion);
           stmt.execute();
           return true;
       }catch(SQLException e){
           e.printStackTrace();
           return false;
       }
    }
}
