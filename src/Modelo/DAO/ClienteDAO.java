package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.MostrarDatosClienteDTO;
import Modelo.Entidad.Cliente;
import Modelo.Enum.TipoCliente;
import java.sql.*;
import java.sql.Types;
import javax.swing.table.DefaultTableModel;

public class ClienteDAO {
   public boolean RegistrarloComoFrecuente(Cliente cliente){
       dbConexion dbc=new dbConexion();
       Connection con = dbc.conectar();
       String sql="{CALL Registrar_Cliente_Frecuente(?,?,?,?,?)}";
       try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setString(1, cliente.getDNI());
            if(cliente.getRUC()!=null && !cliente.getRUC().isEmpty()){
                stmt.setString(2,cliente.getRUC());
            }
            else{
                stmt.setNull(2, Types.VARCHAR);
            }
            stmt.setString(3,cliente.getTelefono());
            stmt.setString(4,cliente.getCorreo());
            stmt.setString(5,cliente.getDireccion());
            stmt.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false; 
        }
   }
   public MostrarDatosClienteDTO MostrarDatosClientes(TipoCliente tCliente,String DNI){
       String sql="{CALL Registrar_Cliente_Frecuente(?,?)}";
       try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
           stmt.setString(1, tCliente.name());
           stmt.setString(2,DNI);
           ResultSet rs=stmt.executeQuery();
           if(rs.next()){
               String estado=rs.getString("Estado");
               if("OK".equals("Estado")){
                   return new MostrarDatosClienteDTO(true,rs.getString("Mensaje"),rs.getString("RUC"),rs.getString("NombreCompleto"));
               }               
           }
       }catch(SQLException e){
            e.printStackTrace();
            return new MostrarDatosClienteDTO("Error en la BD:"+e.getMessage());
        }
       return new MostrarDatosClienteDTO("Error en el servidor");
   }
   
   //===========================================================================
   
   // CU04
   public boolean registrarClienteFrecuente(String dni, String ruc, String telefono, String correo, String direccion) {
        Connection conn = null;
        boolean exito = false;
        String sql = "{CALL Registrar_Cliente_Frecuente(?, ?, ?, ?, ?)}";
        try {
            conn = new dbConexion().conectar();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, (dni != null && !dni.isEmpty()) ? dni : null);
            stmt.setString(2, (ruc != null && !ruc.isEmpty()) ? ruc : null);
            stmt.setString(3, telefono);
            stmt.setString(4, correo);
            stmt.setString(5, direccion);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                exito = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exito;
    }
   
   // CU04
   public DefaultTableModel obtenerHistorialCompras(String dni, String ruc) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Venta");
        modelo.addColumn("Fecha de Compra");
        modelo.addColumn("Nombre Cliente"); 
        String sql = "{CALL Contabilizar_Compras_Mes(?, ?)}";
        try {
            Connection conn = new dbConexion().conectar();
            java.sql.CallableStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, (dni != null && !dni.isEmpty()) ? dni : null);
            stmt.setString(2, (ruc != null && !ruc.isEmpty()) ? ruc : null);
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("ID_Venta");
                fila[1] = rs.getString("Fecha");
                fila[2] = rs.getString("Nombre_Cliente");
                modelo.addRow(fila);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelo;
    }
   
    // CU04
    public int obtenerTotalComprasMes(String dni, String ruc) {
        int totalCompras = 0;
        String sql = "{CALL Contabilizar_Compras_Mes(?, ?)}";
        try {
            Connection conn = new dbConexion().conectar();
            java.sql.CallableStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, (dni != null && !dni.isEmpty()) ? dni : null);
            stmt.setString(2, (ruc != null && !ruc.isEmpty()) ? ruc : null);
            java.sql.ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalCompras = rs.getInt("Total_Compras_Mes");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalCompras;
    }
}

