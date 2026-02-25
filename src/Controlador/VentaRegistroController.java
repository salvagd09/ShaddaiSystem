/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion.dbConexion;
import Modelo.DAO.VentaDAO;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.Entidad.Cliente;
import Modelo.Enum.MetodoPago;
import Modelo.Enum.TipoComprobante;
import Modelo.Enum.TipoVenta;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class VentaRegistroController {
    private VentaDAO venta;
    public String registrarVentaCompleta(int idUsuario, Cliente cliente,TipoVenta tVenta,TipoComprobante tComprobante,
    MetodoPago mPago,int idPedido,double Total,Map<String,Integer> productosYCantidad) {
    String mensaje="";
    if(productosYCantidad == null || productosYCantidad.isEmpty()){
        return "Error.Debe haber como mínimo 1 producto";
    }
    try (Connection conn = new dbConexion().conectar()) {
        conn.setAutoCommit(false); // Inicia transacción
        try {
            int idVentaGenerada = venta.RegistrarVenta(conn,cliente,idUsuario,tVenta,tComprobante,mPago,idPedido,Total);
            if (idVentaGenerada == -1) {
                conn.rollback();
                return "Error en la transacción para el registro inicial en venta";
            }
            for (Map.Entry<String,Integer> entry : productosYCantidad.entrySet()) {
                String NombreProducto = entry.getKey();
                int cantidad = (int) entry.getValue(); 
                mensaje = venta.RegistrarDetalleVenta(
                    conn, idVentaGenerada, NombreProducto, cantidad
                );
                if ("Error en la transacción".equals(mensaje)) {
                    conn.rollback(); 
                    return mensaje+"de registrar detalles de venta";
                }
            }
            conn.commit(); 
            return mensaje;
        } catch (Exception e) {
            conn.rollback();
              return "Error inesperado"+e.getMessage();
        }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error en la conexión";
        }
    }
}
