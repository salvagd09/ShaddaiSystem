
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.DetallesPedidoConfirmadoDTO;
import Modelo.DTO.DetallesPedidoPendienteDTO;
import Modelo.DTO.RegistrarPedidoPendienteDTO;
import Modelo.DTO.ResultadoIDPedidosDTO;
import Modelo.Entidad.Cliente;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.Entidad.DetalleVenta;
import Modelo.Enum.EstadoPedido;
import java.sql.CallableStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

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
             if(cliente.getRUC()!=null && !cliente.getRUC().isEmpty()){
                stmt.setString(4,cliente.getRUC());
            }
            else{
                stmt.setNull(4, Types.VARCHAR);
            }
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
        CallableStatement stmt =  conn.prepareCall(sql)){
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
    
    public ResultadoOperacionDTO ActualizarDetallesPPedidoPendiente(int idPedido,String nombreProducto,int cantidadNueva){
        String sql = "{CALL Actualizar_DP_Pedido_Pendiente(?, ?, ?)}";
    try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt =  conn.prepareCall(sql)) {
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
    
    // CU01
    public boolean registrarPedidoPendienteWhatsapp(String tipoCliente, String dni, String ruc, String nombreCompleto, List<DetalleVenta> carrito) {
        Connection conn = null;
        boolean exito = false;
        
        String sqlCabecera = "{CALL SP_Pedido_Pendiente(?, ?, ?, ?, ?)}";
        String sqlDetalle = "{CALL SP_Pedido_Pendiente_Detalles(?, ?, ?)}";

        try {
            conn = new dbConexion().conectar();
            conn.setAutoCommit(false); 
            int idPedidoGenerado = -1;

            try (CallableStatement stmtCabecera =  conn.prepareCall(sqlCabecera)) {
                stmtCabecera.setString(1, "Whatsapp");
                stmtCabecera.setString(2, tipoCliente);
                
                stmtCabecera.setString(3, (dni != null && !dni.isEmpty()) ? dni : null);
                stmtCabecera.setString(4, (ruc != null && !ruc.isEmpty()) ? ruc : null);
                stmtCabecera.setString(5, nombreCompleto);

                try (ResultSet rs = stmtCabecera.executeQuery()) {
                    if (rs.next() && "OK".equals(rs.getString("Estado"))) {
                        idPedidoGenerado = rs.getInt("ID_Pedido_Generado");
                    } else {
                        conn.rollback();
                        return false; 
                    }
                }
            }

            if (idPedidoGenerado != -1) {
                try (CallableStatement stmtDetalle =  conn.prepareCall(sqlDetalle)) {
                    for (DetalleVenta detalle : carrito) {
                        stmtDetalle.setInt(1, idPedidoGenerado);
                        stmtDetalle.setString(2, detalle.getCodigoProductoTemp());
                        stmtDetalle.setInt(3, detalle.getCantidad());
                        stmtDetalle.execute();
                    }
                }
                
                conn.commit();
                exito = true;
            }

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } 
        return exito;
    }
    
    // CU02
    public DefaultTableModel obtenerIDsPedidosPendiente(String dni) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Pedido Pendiente");
        
        String sql = "{CALL Obtener_ID_Pedidos_Pendiente(?)}";
        try {
            Connection conn = new dbConexion().conectar();
            java.sql.CallableStatement stmt = conn.prepareCall(sql);
            stmt.setString(1, dni);
            
            java.sql.ResultSet rs = stmt.executeQuery(); 
            
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getInt("ID_Pedido")});
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return modelo;
    }
    
    // CU02
    public String[] obtenerDatosCabecera(int idPedido) {
        String[] datos = new String[2];
        String sql = "{CALL Obtener_DatosP_Pedido_Pendiente(?)}";
        try {
            Connection conn = new dbConexion().conectar();
            CallableStatement stmt =  conn.prepareCall(sql);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                datos[0] = rs.getString("NombreCompleto");
                datos[1] = rs.getString("Fecha");
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return datos;
    }

    // CU02
    public DefaultTableModel obtenerDetallesPedido(int idPedido) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Producto");
        modelo.addColumn("Cantidad Solicitada");
        modelo.addColumn("Stock Actual");
        modelo.addColumn("Disponible");

        String sql = "{CALL Obtener_DatosDP_Pedido_Pendiente(?)}";
        try {
            Connection conn = new dbConexion().conectar();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("Nombre"),
                    rs.getInt("cantidad"),
                    rs.getInt("Stock_Tienda"),
                    rs.getString("Disponible")
                });
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return modelo;
    }

    //CU02
    public boolean confirmarPedido(int idPedido, int idUsuario) {
        boolean exito = false;
        
        String sqlObtenerCliente = "SELECT ID_Cliente FROM Pedido WHERE ID_Pedido = ?";
        String sqlLeerDetalles = "SELECT ID_Producto, cantidad, precioUnitario FROM Detalles_Pedido WHERE ID_Pedido = ?";
        String sqlDescontarStock = "UPDATE Producto SET Stock_Tienda = Stock_Tienda - ? WHERE ID_Producto = ?";
        String sqlConfirmar = "UPDATE Pedido SET Estado = 'Confirmado' WHERE ID_Pedido = ?";

        try {
            Connection conn = new dbConexion().conectar();
            conn.setAutoCommit(false); 

            int idCliente = 0;
            double totalVenta = 0.0;

            PreparedStatement pstCliente = conn.prepareStatement(sqlObtenerCliente);
            pstCliente.setInt(1, idPedido);
            ResultSet rsCliente = pstCliente.executeQuery();
            if (rsCliente.next()) {
                idCliente = rsCliente.getInt("ID_Cliente");
            }
            rsCliente.close();
            pstCliente.close();

            PreparedStatement pstLeer = conn.prepareStatement(sqlLeerDetalles);
            pstLeer.setInt(1, idPedido);
            ResultSet rsDetalles = pstLeer.executeQuery();

            PreparedStatement pstDescontar = conn.prepareStatement(sqlDescontarStock);
            while (rsDetalles.next()) {
                int idProd = rsDetalles.getInt("ID_Producto");
                int cant = rsDetalles.getInt("cantidad");
                double precioU = rsDetalles.getDouble("precioUnitario");
                
                totalVenta += (cant * precioU); 

                pstDescontar.setInt(1, cant);
                pstDescontar.setInt(2, idProd);
                pstDescontar.executeUpdate();
            }
            rsDetalles.close();
            pstLeer.close();
            pstDescontar.close();
            PreparedStatement pstConfirmar = conn.prepareStatement(sqlConfirmar);
            pstConfirmar.setInt(1, idPedido);
            pstConfirmar.executeUpdate();
            pstConfirmar.close();

            conn.commit();
            exito = true;
            conn.close();
            
        } catch (Exception e) { 
            System.out.println("Error al confirmar el pedido y generar la venta: " + e.getMessage());
            e.printStackTrace(); 
        }
        return exito;
    }

    // CU02
    public String actualizarCantidadDetalle(int idPedido, String nombreProd, int nuevaCantidad) {
        String mensaje = "Error desconocido";
        String sql = "{CALL Actualizar_DP_Pedido_Pendiente(?, ?, ?)}";
        try {
            Connection conn = new dbConexion().conectar();
            CallableStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, idPedido);
            stmt.setString(2, nombreProd);
            stmt.setInt(3, nuevaCantidad);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mensaje = rs.getString("Mensaje"); 
            }
            conn.close();
        } catch (Exception e) { e.printStackTrace(); }
        return mensaje;
    }
}
