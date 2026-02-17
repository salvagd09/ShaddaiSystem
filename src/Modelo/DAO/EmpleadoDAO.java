/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class EmpleadoDAO {
    public Map<Integer,Integer> LoginPedido(String username,String password){
        Map<Integer,Integer> RolEID=new HashMap<>();
        String sql="{CALL Login_Rol(?,?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)) {
            stmt.setString(1, username);
            stmt.setString(2,password);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                RolEID.put(rs.getInt("ID_Usuario"), rs.getInt("ID_Rol"));
            }
        }catch(SQLException e){
            e.printStackTrace();
           throw new Error("Error en la base de datos: " + e.getMessage());
        }
        return RolEID;
    }
}
