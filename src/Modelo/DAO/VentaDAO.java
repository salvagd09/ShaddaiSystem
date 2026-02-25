/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Conexion.dbConexion;
import Modelo.DTO.DatosParaClienteFrecuenteDTO;
import Modelo.DTO.ReporteVentasDTO;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.DTO.VentaCategoriaDTO;
import Modelo.DTO.VentaProductosDTO;
import Modelo.DTO.VentasVendedorDTO;
import Modelo.Entidad.Cliente;
import Modelo.Entidad.DetalleVenta;
import Modelo.Enum.MetodoPago;
import Modelo.Enum.TipoComprobante;
import Modelo.Enum.TipoVenta;
import java.sql.CallableStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class VentaDAO {
    public ReporteVentasDTO GenerarReporteVenta(String nombreCategoria,String nombreVendedor,Date Fecha){
        String sql="{CALL SP_Generar_Reporte_Ventas(?,?,?)}";
        ReporteVentasDTO reporte = new ReporteVentasDTO();
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt =  conn.prepareCall(sql)){
            if (nombreCategoria != null && !nombreCategoria.isEmpty()) {
                stmt.setString(1, nombreCategoria);
            } else {
                stmt.setNull(1, Types.VARCHAR);
            }
            if (nombreVendedor != null && !nombreVendedor.isEmpty()) {
                stmt.setString(2, nombreVendedor);
            } else {
                stmt.setNull(2, Types.VARCHAR);
            }
            if (Fecha != null) {
                stmt.setDate(3, new java.sql.Date(Fecha.getTime()));
            } else {
                stmt.setNull(3, Types.DATE);
            }
            boolean tieneResultados=stmt.execute();
            if(tieneResultados){
                ResultSet rs1=stmt.getResultSet();
                List<VentaProductosDTO> ventasProducto=new ArrayList<>();
                while(rs1.next()){
                    VentaProductosDTO venta = new VentaProductosDTO(
                        rs1.getString("Nombre"),
                        rs1.getString("nombre_Categoria"),
                        rs1.getInt("Total_Vendido"),
                        rs1.getDouble("Monto_Total")
                    );
                    ventasProducto.add(venta);
                }
                reporte.setVentasPorProducto(ventasProducto);
                if(stmt.getMoreResults()){
                    ResultSet rs2=stmt.getResultSet();
                    List<VentaCategoriaDTO> ventasCategoria=new ArrayList<>();
                    while(rs2.next()){
                        VentaCategoriaDTO venta=new VentaCategoriaDTO(rs2.getString("nombre_Categoria"),
                        rs2.getDouble("Monto_Total"),
                        rs2.getInt("Num_Ventas"));
                        ventasCategoria.add(venta);
                    }
                    reporte.setVentasPorCategoria(ventasCategoria);
                }
                if(stmt.getMoreResults()){
                    ResultSet rs3=stmt.getResultSet();
                    List<VentasVendedorDTO> ventasVendedor=new ArrayList<>();
                    while(rs3.next()){
                        VentasVendedorDTO venta=new VentasVendedorDTO(
                        rs3.getString("Vendedor"),
                        rs3.getInt("Cantidad_Ventas"),
                        rs3.getDouble("Monto_Total"));
                        ventasVendedor.add(venta);
                    }
                    reporte.setVentasPorVendedor(ventasVendedor);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reporte;
    }
    public int RegistrarVenta(Cliente cliente,int idUsuario,TipoVenta tVenta,TipoComprobante tComprobante,
    MetodoPago mPago,int idPedido,double Total){
        String sql="{CALL Registrar_Venta_General(?,?,?,?,?,?,?,?,?,?)}";
        int idVentaGenerada=0;
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt =  conn.prepareCall(sql)){
            stmt.setInt(1,idPedido);
            stmt.setInt(2, idUsuario);
            stmt.setString(3,cliente.getDNI());
            if(cliente.getRUC()!=null && !cliente.getRUC().isEmpty()){
                stmt.setString(4,cliente.getRUC());
            }
            else{
                stmt.setNull(4, Types.VARCHAR);
            }
            stmt.setDouble(5,Total);
            stmt.setString(6,cliente.getNombreCompleto());
            stmt.setString(7,cliente.getTipoCliente().name());
            stmt.setString(8, tVenta.name());
            stmt.setString(9,tComprobante.name());
            stmt.setString(10,mPago.name());
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                idVentaGenerada=rs.getInt("ID_Venta_Generada");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en BD: "+e.getMessage());
        }
        return idVentaGenerada;
    }
    public String RegistrarDetalleVenta(int idVentaGenerada,String nombreProducto,int Cantidad){
        String sql="{CALL Registrar_Detalle_Venta(?,?,?)}";
        String mensajeFinal="";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt =  conn.prepareCall(sql)){
            stmt.setInt(1,idVentaGenerada);
            stmt.setString(2,nombreProducto);
            stmt.setInt(3, Cantidad);
            ResultSet rs=stmt.executeQuery();
            if(rs.next()){
                mensajeFinal=rs.getString("Mensaje");
            }else{
                mensajeFinal=rs.getString("Error");
            }
        }catch(SQLException e){
            e.printStackTrace();
            throw new Error("Error en BD:"+e.getMessage());
        }
        return mensajeFinal;
    }

    public DatosParaClienteFrecuenteDTO ContabilizarVentasMes(String DNI,String RUC){
        String sql="{CALL Contabilizar_Compras_Mes(?,?)}";
        try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt =  conn.prepareCall(sql)){
            if(DNI!= null && !RUC.isEmpty()){
                stmt.setString(1, DNI);
            }
            else {
                stmt.setNull(1, Types.VARCHAR);
            } 
             if(RUC!= null && !RUC.isEmpty()){
                 stmt.setString(2, RUC);
             }
             else {
                stmt.setNull(2, Types.VARCHAR);
            }   
             ResultSet rs=stmt.executeQuery();
             if(rs.next()){
                 return new DatosParaClienteFrecuenteDTO(rs.getString("Nombre_Cliente"),
                 rs.getInt("ID_Venta"),rs.getDate("Fecha"),rs.getInt("Total_Compra_Mes"));
             }
         }
        catch(SQLException e){
            e.printStackTrace(); 
            return new DatosParaClienteFrecuenteDTO("Error en la BD:"+e.getMessage());
        }
        return new DatosParaClienteFrecuenteDTO("Error en el servidor");
    }
    
    // CU01
    // Valida si la cantidad vendida esta disponible.
    public ResultadoOperacionDTO validarStockTienda(String codigoProducto, int cantidad) {
        ResultadoOperacionDTO resultado = new ResultadoOperacionDTO();
        String sql = "{CALL Validar_Cantidad_Tienda(?, ?)}";

        try (Connection conn = new dbConexion().conectar();
             CallableStatement stmt =  conn.prepareCall(sql)) {

            stmt.setString(1, codigoProducto);
            stmt.setInt(2, cantidad);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    resultado.setEstado(rs.getString("Estado"));
                    resultado.setMensaje(rs.getString("Mensaje"));
                    if ("OK".equals(resultado.getEstado())) {
                        resultado.setNombreProducto(rs.getString("Nombre"));
                        resultado.setPrecio(rs.getDouble("Precio_Unitario"));
                        resultado.setSubtotal(rs.getDouble("Subtotal"));
                    }
                }
            }
        } catch (SQLException e) {
            resultado.setEstado("Error");
            resultado.setMensaje("Error en BD: " + e.getMessage());
        }
        return resultado;
    }

    // CU01
    // Finalizar Venta 
    public boolean registrarVentaCompleta(String tipoVenta, int idUsuario, String tipoCliente, String dniCliente, String rucCliente, String nombreCliente, 
                                          String tipoComprobante, String metodoPago, 
                                          List<DetalleVenta> carrito) {
        Connection conn = null;
        boolean exito = false;
        
        String sqlCabecera = "{CALL Registrar_Venta(?, ?, ?, ?, ?, ?, ?, ?)}";
        String sqlDetalle = "{CALL Registrar_DetallesVenta(?, ?, ?)}";

        try {
            conn = new dbConexion().conectar();
            conn.setAutoCommit(false); 
            int idVentaGenerado = -1;
            try (CallableStatement stmtCabecera =  conn.prepareCall(sqlCabecera)) {
                stmtCabecera.setString(1, tipoVenta);
                stmtCabecera.setInt(2, idUsuario);
                stmtCabecera.setString(3, tipoCliente);
                
                if (dniCliente == null || dniCliente.trim().isEmpty()) {
                    stmtCabecera.setNull(4, java.sql.Types.VARCHAR);
                } else {
                    stmtCabecera.setString(4, dniCliente);
                }
                
                if (rucCliente == null || rucCliente.trim().isEmpty()) {
                    stmtCabecera.setNull(5, java.sql.Types.VARCHAR);
                } else {
                    stmtCabecera.setString(5, rucCliente);
                }
                
                stmtCabecera.setString(6, (nombreCliente == null || nombreCliente.trim().isEmpty()) ? null : nombreCliente);
                
                stmtCabecera.setString(7, tipoComprobante);
                stmtCabecera.setString(8, metodoPago);

                try (ResultSet rs = stmtCabecera.executeQuery()) {
                    if (rs.next() && "OK".equals(rs.getString("Estado"))) {
                        idVentaGenerado = rs.getInt("ID_Venta_Generado");
                    } else {
                        conn.rollback();
                        return false; 
                    }
                }
            }

            if (idVentaGenerado != -1) {
                try (CallableStatement stmtDetalle = (CallableStatement) conn.prepareCall(sqlDetalle)) {
                    for (DetalleVenta detalle : carrito) {
                        stmtDetalle.setInt(1, idVentaGenerado);
                        stmtDetalle.setString(2, detalle.getCodigoProductoTemp()); 
                        stmtDetalle.setInt(3, detalle.getCantidad());
                        stmtDetalle.execute();
                    }
                }
                
                conn.commit();
                exito = true;
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return exito;
    }
}
