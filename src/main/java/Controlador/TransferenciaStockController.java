/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion.dbConexion;
import Modelo.DAO.ProductoDAO;
import Modelo.DAO.TransferenciaDAO;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.DTO.ValidarCantidadAlmacenDTO;
import java.util.List;
import java.sql.*;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class TransferenciaStockController {
    private ProductoDAO productoDAO;
    private TransferenciaDAO transferenciaDAO;
    public TransferenciaStockController(){
        this.productoDAO = new ProductoDAO();
        this.transferenciaDAO=new TransferenciaDAO();
    }
    public ResultadoOperacionDTO registrarTransferenciaCompleta(int idUsuario, Map<Integer, double[]> productosYDatos) {
    if (productosYDatos == null || productosYDatos.isEmpty()) {
        return new ResultadoOperacionDTO("Error", "Debe ingresar al menos 1 producto");
    }
    try (Connection conn = new dbConexion().conectar()) {
        conn.setAutoCommit(false); // Inicia transacción
        try {
            int idTransferencia = transferenciaDAO.RegistrarTransferencia(conn, idUsuario);
            if (idTransferencia == -1) {
                conn.rollback();
                return new ResultadoOperacionDTO("Error", "No se pudo realizar la transferencia");
            }
            for (Map.Entry<Integer, double[]> entry : productosYDatos.entrySet()) {
                int idProducto = entry.getKey();
                int cantidad = (int) entry.getValue()[0];
                double subtotal = entry.getValue()[1]; 

                boolean exito = transferenciaDAO.RegistrarDetalleTransferencia(
                    conn, idTransferencia, idProducto, cantidad, subtotal
                );

                if (!exito) {
                    conn.rollback(); 
                    return new ResultadoOperacionDTO("Error", "Error al agregar producto ID: " + idProducto);
                }
            }
            conn.commit(); 
            return new ResultadoOperacionDTO("OK",
                "Transferencia #" + idTransferencia + " registrada con "
                + productosYDatos.size() + " productos");
        } catch (Exception e) {
            conn.rollback();
            return new ResultadoOperacionDTO("Error", "Error inesperado: " + e.getMessage());
        }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultadoOperacionDTO("Error", "Error de conexión");
        }
    }
    public ValidarCantidadAlmacenDTO ValidarCantidadTrasladar(String nombreProducto,int Cantidad){
        return productoDAO.ValidarCantidadProductoAlmacen(nombreProducto, Cantidad);
    }
    public List<String> ObtenerNombresProductos(){
        return productoDAO.ObtenerNombresProductoTrasladar();
    }
}
