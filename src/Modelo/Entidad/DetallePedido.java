/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

/**
 *
 * @author Usuario
 */
public class DetallePedido {
    private int IDDetallePedido;
    private int IDPedido;
    private int IDProducto;
    private int cantidad;
    private double precioUnitario;
    public int getIDDetallePedido() {
        return IDDetallePedido;
    }
    public void setIDDetallePedido(int IDDetallePedido) {
        this.IDDetallePedido = IDDetallePedido;
    }
    public int getIDPedido() {
        return IDPedido;
    }
    public void setIDPedido(int IDPedido) {
        this.IDPedido = IDPedido;
    }
    public int getIDProducto() {
        return IDProducto;
    }
    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
