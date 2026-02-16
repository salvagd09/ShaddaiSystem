/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

/**
 *
 * @author Usuario
 */
public class DetalleVenta {
    private int IDDetalleVenta;
    private int IDVenta;
    private int IDProducto;
    private int Cantidad;
    private double PrecioUnitario;
    public int getIDDetalleVenta() {
        return IDDetalleVenta;
    }
    public void setIDDetalleVenta(int IDDetalleVenta) {
        this.IDDetalleVenta = IDDetalleVenta;
    }
    public int getIDVenta() {
        return IDVenta;
    }
    public void setIDVenta(int IDVenta) {
        this.IDVenta = IDVenta;
    }
    public int getIDProducto() {
        return IDProducto;
    }
    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
    public double getPrecioUnitario() {
        return PrecioUnitario;
    }
    public void setPrecioUnitario(double PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }
}
