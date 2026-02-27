/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.CategoriaDAO;
import Modelo.DAO.EmpleadoDAO;
import Modelo.DAO.ProductoDAO;
import Modelo.DAO.VentaDAO;
import Modelo.DTO.ReporteVentasDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ReporteVentasController {
    private VentaDAO ventaDAO;
    private CategoriaDAO categoriaDAO;
    private ProductoDAO productoDAO;
    private EmpleadoDAO empleadoDAO;

    public ReporteVentasController() {
        this.ventaDAO = new VentaDAO();
        this.categoriaDAO = new CategoriaDAO();
        this.productoDAO = new ProductoDAO();
        this.empleadoDAO = new EmpleadoDAO();
    }

    public List<String> ObtenerNombresProducto(){
        return  productoDAO.ObtenerNombresProducto();
    }
     public List<String> obtenerNombresCategorias(){
         return categoriaDAO.obtenerNombresCategorias();
     }
     public List<String> ObtenerNombresVendedor(){
         return empleadoDAO.ObtenerNombresVendedor();
     }
    public ReporteVentasDTO GenerarReporteVenta(String nombreCategoria,String nombreVendedor,Date Fecha){
        return ventaDAO.GenerarReporteVenta(nombreCategoria, nombreVendedor, Fecha);
    }
}
