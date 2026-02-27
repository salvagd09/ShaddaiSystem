/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

/**
 *
 * @author Usuario
 */
public class DetalleNotificacion {
    private int IDDetalleNotificacion;
    private int IDNotificacion;
    private int IDProducto;
    private int StockActual;
    public int getIDDetalleNotificacion() {
        return IDDetalleNotificacion;
    }
    public void setIDDetalleNotificacion(int IDDetalleNotificacion) {
        this.IDDetalleNotificacion = IDDetalleNotificacion;
    }
    public int getIDNotificacion() {
        return IDNotificacion;
    }
    public void setIDNotificacion(int IDNotificacion) {
        this.IDNotificacion = IDNotificacion;
    }
    public int getIDProducto() {
        return IDProducto;
    }
    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }
    public int getStockActual() {
        return StockActual;
    }
    public void setStockActual(int StockActual) {
        this.StockActual = StockActual;
    }
}
