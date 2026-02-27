/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

import Modelo.Enum.MetodoPago;
import Modelo.Enum.TipoComprobante;
import Modelo.Enum.TipoVenta;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Venta {
    private int IDVenta;
    private int IDPedido;
    private int IDUsuario;
    private int IDCliente;
    private Date Fecha;
    private double Total;
    private TipoComprobante Comprobante;
    private TipoVenta TVenta;
    private MetodoPago MetPago;
    public int getIDVenta() {
        return IDVenta;
    }
    public void setIDVenta(int IDVenta) {
        this.IDVenta = IDVenta;
    }
    public int getIDPedido() {
        return IDPedido;
    }
    public void setIDPedido(int IDPedido) {
        this.IDPedido = IDPedido;
    }
    public int getIDUsuario() {
        return IDUsuario;
    }
    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }
    public int getIDCliente() {
        return IDCliente;
    }
    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }
    public Date getFecha() {
        return Fecha;
    }
    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public double getTotal() {
        return Total;
    }
    public void setTotal(double Total) {
        this.Total = Total;
    }
    public TipoComprobante getComprobante() {
        return Comprobante;
    }
    public void setComprobante(TipoComprobante Comprobante) {
        this.Comprobante = Comprobante;
    }
    public TipoVenta getTVenta() {
        return TVenta;
    }
    public void setTVenta(TipoVenta TVenta) {
        this.TVenta = TVenta;
    }
    public MetodoPago getMetPago() {
        return MetPago;
    }
    public void setMetPago(MetodoPago MetPago) {
        this.MetPago = MetPago;
    }
}
