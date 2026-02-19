
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.DetallesPedidoConfirmadoDTO;
import Modelo.DTO.DetallesPedidoPendienteDTO;
import Modelo.DTO.RegistrarPedidoPendienteDTO;
import Modelo.DTO.ResultadoIDPedidosDTO;
import Modelo.Entidad.Cliente;
import Modelo.Entidad.DetallePedido;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.Enum.EstadoPedido;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoDAO {
    public RegistrarPedidoPendienteDTO AgregarPedidoPendiente(Cliente cliente,EstadoPedido tipoP){
       dbConexion dbc=new dbConexion();
       Connection con = dbc.conectar();
       String sql="{CALL SP_Pedido_Pendiente(?,?,?,?,?)";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setString(1, tipoP.name());
            stmt.setString(2,cliente.getTipoCliente().name());
            stmt.setString(3, cliente.getDNI());
            stmt.setString(4, cliente.getRUC());
            stmt.setString(5,cliente.getNombreCompleto());
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                return new RegistrarPedidoPendienteDTO(rs.getString("Estado"),
                rs.getString("Mensaje"),
                rs.getInt("ID_Pedido_Generado"));
            }
        }catch(SQLException e){
            e.printStackTrace();
            return new RegistrarPedidoPendienteDTO("Error", "Error en la base de datos: " + e.getMessage(),0);
        }
        return new RegistrarPedidoPendienteDTO("Error","No se pudo obtener respuesta del servidor.",-1);
    };
    public boolean AgregarDetallesPedidoPendiente(int idPedido,String nombreProducto,int Cantidad){
        String sql="{CALL SP_Pedido_Pendiente_Detalles(?,?,?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setInt(1,idPedido);
            stmt.setString(2,nombreProducto);
            stmt.setInt(3, Cantidad);
            stmt.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
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
    public List<DetallesPedidoConfirmadoDTO> ObtenerDetallesPConfirmado(int idPedido){
       String sql="{CALL Obtener_DatosDP_Pedido_Confirmado(?)}";
       List<DetallesPedidoConfirmadoDTO> listaDPedidosC = new ArrayList<>();
       try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
           stmt.setInt(1, idPedido);
           ResultSet rs=stmt.executeQuery();
           while(rs.next()){
               DetallesPedidoConfirmadoDTO dto = new DetallesPedidoConfirmadoDTO();
               dto.setNombre(rs.getString("Nombre"));
               dto.setCantidad(rs.getInt("cantidad"));
               dto.setPrecio(rs.getDouble("Precio Unitario"));
               dto.setSubTotal(rs.getDouble("Subtotal"));
               listaDPedidosC.add(dto);
           }
       }catch(SQLException e){
           e.printStackTrace();
           throw new Error("Error en la base de datos: "+e.getMessage());
       }
       return listaDPedidosC;
    }
    public ResultadoIDPedidosDTO ObtenerIDsPedidosPendientes(String DNI){
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
                        return new ResultadoIDPedidosDTO(false, mensaje, new ArrayList<>());
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
                        return new ResultadoIDPedidosDTO(
                            "OK".equals(estado), 
                            mensaje, 
                            ids
                        );
                    }
                }
                return new ResultadoIDPedidosDTO(true, "Pedidos encontrados", ids);
        } 
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultadoIDPedidosDTO("Error en la base de datos: " + e.getMessage());
        }
        return new ResultadoIDPedidosDTO("No se pudo obtener respuesta del servidor");
    }
    public Map<String,Date> ObtenerPedidoPendiente(int idPedido){
        String sql="{CALL  Obtener_DatosP_Pedido_Pendiente(?)}";
        Map<String,Date> NombreYFecha=new HashMap<>();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                NombreYFecha.put(rs.getString("NombreCompleto"), rs.getDate("Fecha"));
            }
        }catch (SQLException e) {
        e.printStackTrace();
            throw new Error("Error en la base de datos: " + e.getMessage());
        }
        return NombreYFecha;
    }
    public List<DetallesPedidoPendienteDTO> ObtenerDetallesPPendientes(int idPedido){
       String sql="{CALL Obtener_DatosDP_Pedido_Confirmado(?)}";
       List<DetallesPedidoPendienteDTO> listaDPedidos = new ArrayList<>();
       try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
           stmt.setInt(1, idPedido);
           ResultSet rs=stmt.executeQuery();
           while(rs.next()){
               DetallesPedidoPendienteDTO dto = new DetallesPedidoPendienteDTO();
               dto.setNombreProducto(rs.getString("Nombre"));
               dto.setCantidad(rs.getInt("cantidad"));
               dto.setStockTienda(rs.getInt("Stock_Tienda"));
               listaDPedidos.add(dto);
           }
       }catch(SQLException e){
           e.printStackTrace();
           throw new Error("Error en la base de datos: "+e.getMessage());
       }
       return listaDPedidos;
    }
    public boolean ConfirmarPedido(int idPedido){
        String sql="{CALL Confirmar_Pedido(?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setInt(1,idPedido);
            stmt.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public ResultadoOperacionDTO ActualizarDetallesPPedidoPendiente(int idPedido,String nombreProducto,int cantidadNueva){
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
            return new ResultadoOperacionDTO(estado, mensaje);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return new ResultadoOperacionDTO("Error", "Error en la base de datos: " + e.getMessage());
    }
    return new ResultadoOperacionDTO("Error", "No se pudo obtener respuesta del servidor");
    }
}
