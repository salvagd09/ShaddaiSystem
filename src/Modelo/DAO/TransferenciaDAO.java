/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class TransferenciaDAO {
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
}
