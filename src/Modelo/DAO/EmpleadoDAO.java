/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.LoginResultDTO;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class EmpleadoDAO {
    public LoginResultDTO LoginPedido(String username,String password){
        Map<Integer,String> RolEID=new HashMap<>();
        String sql="{CALL Login_Rol(?,?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)) {
            stmt.setString(1, username);
            stmt.setString(2,password);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                int idUsuario=rs.getInt("ID_USUARIO");
                String NombreRol=rs.getString("NombreRol");
                return new LoginResultDTO(idUsuario,NombreRol);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<String> ObtenerNombresVendedor(){
        String sql="{CALL Obtener_Nombres_Vendedor()}";
        List<String> nombresV=new ArrayList<>();
         try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                nombresV.add(rs.getString("Vendedor"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en BD: "+e.getMessage());
        }
         return nombresV;
    }
}
