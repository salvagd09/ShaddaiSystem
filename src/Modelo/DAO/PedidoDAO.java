
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.Entidad.Cliente;
import Modelo.Entidad.DetallePedido;
import Modelo.Entidad.ResultadoAgregacion;
import Modelo.Entidad.ResultadoIDPedidosPendientes;
import Modelo.Entidad.ResultadoOperacion;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    public ResultadoAgregacion AgregarPedidoPendiente(Cliente cliente){
       dbConexion dbc=new dbConexion();
       Connection con = dbc.conectar();
       String sql="{CALL SP_Pedido_Pendiente(?,?,?,?,?)";
       return new ResultadoAgregacion("OK","Hola",5);
    };
    public void AgregarDetallesPedidoPendiente(int idPedido,String codigoProducto,int Cantidad){
        String sql="{CALL SP_Pedido_Pendiente_Detalles(?,?,?)}";
    }
    public Cliente ObtenerPedidoConfirmado(int IDPedido){
        String sql="{CALL Obtener_DatosP_Pedido_Confirmado(?)}";
        Cliente cliente=new Cliente();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setInt(1,IDPedido);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                cliente.setNombreCompleto("NombreCompleto");
                cliente.setDNI("DNI");
                cliente.setRUC("RUC");
            }
        }catch(SQLException e){
           e.printStackTrace();
           throw new Error("Error en la base de datos: " + e.getMessage());
        }
        return cliente;
    }
    public List<DetallePedido> ObtenerDetallesPConfirmado(int idPedido){
       String sql="{CALL Obtener_DatosDP_Pedido_Confirmado(?)}";
       List<DetallePedido> detallesPConfirmado=new ArrayList<>();
       return detallesPConfirmado;
    }
    public ResultadoIDPedidosPendientes ObtenerIDsPedidosPendientes(String DNI){
        String sql = "{CALL Obtener_ID_Pedidos_Pendiente(?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)) {
            stmt.setString(1, DNI);
            //Obtener primer ResultRest(IDs)
            ResultSet rs1 = stmt.executeQuery();
            if (rs1.getMetaData().getColumnCount() == 2 && 
                rs1.getMetaData().getColumnName(1).equals("Estado")) {
                if (rs1.next()) {
                    String estado = rs1.getString("Estado");
                    String mensaje = rs1.getString("Mensaje");
                    if ("Error".equals(estado)) {
                        return new ResultadoIDPedidosPendientes(false, mensaje, new ArrayList<>());
                    }
                }
            } else {
                List<Integer> ids = new ArrayList<>();
                while (rs1.next()) {
                    ids.add(rs1.getInt("ID_Pedido"));
                }
                // Obtener segundo ResultSet (Estado y Mensaje)
                if (stmt.getMoreResults()) {
                    ResultSet rs2 = stmt.getResultSet();
                    if (rs2.next()) {
                        String estado = rs2.getString("Estado");
                        String mensaje = rs2.getString("Mensaje");
                        return new ResultadoIDPedidosPendientes(
                            "OK".equals(estado), 
                            mensaje, 
                            ids
                        );
                    }
                }
                return new ResultadoIDPedidosPendientes(true, "Pedidos encontrados", ids);
        } 
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultadoIDPedidosPendientes("Error en la base de datos: " + e.getMessage());
        }
        return new ResultadoIDPedidosPendientes("No se pudo obtener respuesta del servidor");
    }
    public void ObtenerPedidoPendiente(int idPedido){
        String sql="{CALL  Obtener_DatosP_Pedido_Pendiente(?)}";
    }
    public List<DetallePedido> ObtenerDetallesPPendientes(int idPedido,String codigo_Producto,int cantidad){
        List<DetallePedido> detallesPPendiente=new ArrayList<>();
        String sql="{CALL  Obtener_DatosDP_Pedido_Pendiente(?)}";
       return detallesPPendiente;
    }
    public boolean ConfirmarPedido(int idPedido){
        return true;
    }
    public ResultadoOperacion ActualizarDetallesPPedidoPendiente(int idPedido,String nombreProducto,int cantidadNueva){
        String sql = "{CALL Actualizar_DP_Pedido_Pendiente(?, ?, ?)}";
    try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)) {
        stmt.setInt(1, idPedido);
        stmt.setString(2, nombreProducto);
        stmt.setInt(3, cantidadNueva);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String estado = rs.getString("Estado");
            String mensaje = rs.getString("Mensaje");
            return new ResultadoOperacion(estado, mensaje);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return new ResultadoOperacion("Error", "Error en la base de datos: " + e.getMessage());
    }
    
    return new ResultadoOperacion("Error", "No se pudo obtener respuesta del servidor");
    }
}
