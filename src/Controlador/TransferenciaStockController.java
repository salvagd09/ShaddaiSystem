/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.ProductoDAO;
import Modelo.DAO.TransferenciaDAO;
import Modelo.DTO.ResultadoOperacionDTO;
import Modelo.DTO.ValidarCantidadAlmacenDTO;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
    public ResultadoOperacionDTO registrarTransferenciaCompleta(int idUsuario,Map<Integer,Integer> productosYCantidades){
        /*En caso no hay producto alguno ingresado*/
        if(productosYCantidades==null || productosYCantidades.isEmpty()){
            return new ResultadoOperacionDTO("Error","Debe ingresar al menos 1 producto");
        }
        int idTransferencia=transferenciaDAO.RegistrarTransferencia(idUsuario);
        /*Representa el catch del método RegistrarTransferencia*/
        if(idTransferencia==-1){
            return new ResultadoOperacionDTO("Error","No se pudo realizar la transferencia");
        }
        /*Ayuda a registrar transferencia de varios productos*/
        for(Map.Entry<Integer,Integer> productoYCantidad:productosYCantidades.entrySet()){
            int idProducto =productoYCantidad.getKey();
            int cantidad = productoYCantidad.getValue();
               boolean exito = transferenciaDAO.RegistrarDetalleTransferencia(
                idTransferencia,
                idProducto,
                cantidad
            );
            /*En caso haya un error al registrar la transferencia de 1 producto en específico*/
            if(!exito){
                   return new ResultadoOperacionDTO("Error", 
                    "Error al agregar producto ID: " + idProducto);
            }
        }
        /*En caso de que todos los productos hayan podido ser registrados*/
        return new ResultadoOperacionDTO("OK", 
            "Transferencia #" + idTransferencia + " registrada con " 
            + productosYCantidades.size() + " productos");
    }
    public ValidarCantidadAlmacenDTO ValidarCantidadTrasladar(String nombreProducto,int Cantidad){
        return productoDAO.ValidarCantidadProductoAlmacen(nombreProducto, Cantidad);
    }
    public List<String> ObtenerNombresProductos(){
        return productoDAO.ObtenerNombresProductoTrasladar();
    }
}
