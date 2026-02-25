/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion.dbConexion;
import Modelo.DAO.PedidoDAO;
import Modelo.DTO.RegistrarPedidoPendienteDTO;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.Entidad.Cliente;
import Modelo.Enum.EstadoPedido;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class ConfirmarPedidosController {
    private PedidoDAO pedido;
    public ResultadoOperacionDTO RegistrarPedidoPendienteCompleto(Cliente cliente,EstadoPedido tipoP,
    Map<String,Integer> nombresYCantidad){
        if(nombresYCantidad == null || nombresYCantidad.isEmpty()){
            return new ResultadoOperacionDTO("Error", "Debe ingresar al menos 1 producto");
        }
        try (Connection conn = new dbConexion().conectar()) {
        conn.setAutoCommit(false); // Inicia transacción
        try {
            RegistrarPedidoPendienteDTO pedidoDTO = pedido.AgregarPedidoPendiente(conn,cliente,tipoP);
            if (pedidoDTO.getIDPedido() == -1) {
                conn.rollback();
                throw new Error( "No se pudo realizar la transferencia");
            }
            for (Map.Entry<String,Integer> entry : nombresYCantidad.entrySet()) {
                String NombreProducto = entry.getKey();
                int cantidad = (int) entry.getValue(); 
                boolean exitoso = pedido.AgregarDetallesPedidoPendiente(conn, pedidoDTO.getIDPedido(), NombreProducto, cantidad);
                if (!exitoso) {
                    conn.rollback(); 
                    return new ResultadoOperacionDTO("Error", "Error al agregar producto:"+NombreProducto);
                }
            }
            conn.commit(); 
             return new ResultadoOperacionDTO("OK",
                "Pedido #" + pedidoDTO.getIDPedido() + " registrada con "
                + nombresYCantidad.size() + " productos");
        } catch (Exception e) {
            conn.rollback();
              throw new Error( "Error inesperado: " + e.getMessage());
        }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error("Error en la conexión");
        }
    }
}
