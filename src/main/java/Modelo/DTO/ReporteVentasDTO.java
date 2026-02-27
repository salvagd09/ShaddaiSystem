/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ReporteVentasDTO {
    private List<VentaProductosDTO> ventasPorProducto;
    private List<VentaCategoriaDTO> ventasPorCategoria;
    private List<VentasVendedorDTO> ventasPorVendedor;
    public ReporteVentasDTO() {
        this.ventasPorProducto=new ArrayList<>();
        this.ventasPorCategoria=new ArrayList<>();
        this.ventasPorVendedor=new ArrayList<>();
    }
     public ReporteVentasDTO(List<VentaProductosDTO> ventasPorProducto,List<VentaCategoriaDTO> ventasPorCategoria,List<VentasVendedorDTO> ventasPorVendedor) {
        this.ventasPorProducto = ventasPorProducto;
        this.ventasPorCategoria = ventasPorCategoria;
        this.ventasPorVendedor = ventasPorVendedor;
    }
    public List<VentaProductosDTO> getVentasPorProducto() {
        return ventasPorProducto;
    }
    public void setVentasPorProducto(List<VentaProductosDTO> ventasPorProducto) {
        this.ventasPorProducto = ventasPorProducto;
    }
    public List<VentaCategoriaDTO> getVentasPorCategoria() {
        return ventasPorCategoria;
    }
    public void setVentasPorCategoria(List<VentaCategoriaDTO> ventasPorCategoria) {
        this.ventasPorCategoria = ventasPorCategoria;
    }
    public List<VentasVendedorDTO> getVentasPorVendedor() {
        return ventasPorVendedor;
    }
    public void setVentasPorVendedor(List<VentasVendedorDTO> ventasPorVendedor) {
        this.ventasPorVendedor = ventasPorVendedor;
    }
     public boolean tieneResultados() {
        return !ventasPorProducto.isEmpty() || 
               !ventasPorCategoria.isEmpty() || 
               !ventasPorVendedor.isEmpty();
    } 
}
