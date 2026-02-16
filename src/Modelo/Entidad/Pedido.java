/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

import Modelo.Enum.EstadoPedido;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Pedido {
    private int IDPedido;
    private int IDCliente;
    private EstadoPedido pedido;
    private Date Fecha;

    public int getIDPedido() {
        return IDPedido;
    }

    public void setIDPedido(int IDPedido) {
        this.IDPedido = IDPedido;
    }

    public int getIDCliente() {
        return IDCliente;
    }

    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }

    public EstadoPedido getPedido() {
        return pedido;
    }

    public void setPedido(EstadoPedido pedido) {
        this.pedido = pedido;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }
    
}
