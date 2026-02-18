/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class DetallesPedidoPendienteDTO {
    private String nombreProducto;
    private int cantidad;
    private int stockTienda;
    private String disponible;
    public DetallesPedidoPendienteDTO() {
    }
    public String getNombreProducto() {
        return nombreProducto;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getStockTienda() {
        return stockTienda;
    }
    public void setStockTienda(int stockTienda) {
        this.stockTienda = stockTienda;
    }
    public String getDisponible() {
        return disponible;
    }
    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }
}
