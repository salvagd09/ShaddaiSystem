/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class CategoriaDAO {
    public List<String> obtenerNombresCategorias(){
        String sql="SELECT nombre_Categoria from Categoria";
        List<String> nombresCategoria=new ArrayList<>();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
         ResultSet rs=stmt.executeQuery();
         while(rs.next()){
             nombresCategoria.add(rs.getString("nombre_Categoria"));
         }
        }
        catch(SQLException e){
          e.printStackTrace();
           throw new Error("Error en BD: " + e.getMessage());
    }
        return nombresCategoria;
}
}
