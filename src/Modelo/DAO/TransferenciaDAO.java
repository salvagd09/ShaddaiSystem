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
    public int RegistrarTransferencia(Connection conn,int idUsuario){
        String sql="{CALL Registrar_Transferencia (?)}";
        int idTransferencia=0;
        try {
            CallableStatement stmt = (CallableStatement) conn.prepareCall(sql);
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
    public boolean RegistrarDetalleTransferencia(Connection conn,int idTransferencia,int idProducto,int Cantidad,double Subtotal){
        String sql="{CALL Registrar_Detalle_Transferencia(?,?,?,?)}";
        try 
        {
            CallableStatement stmt = (CallableStatement) conn.prepareCall(sql);
            stmt.setInt(1,idTransferencia);
            stmt.setInt(2,idProducto);
            stmt.setInt(3, Cantidad);
            stmt.setDouble(4, Subtotal);
            stmt.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
