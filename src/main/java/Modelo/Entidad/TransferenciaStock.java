/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class TransferenciaStock {
    private int IDTransferencia;
    private int IDUsuario;
    private Date Fecha;
    public int getIDTransferencia() {
        return IDTransferencia;
    }
    public void setIDTransferencia(int IDTransferencia) {
        this.IDTransferencia = IDTransferencia;
    }
    public int getIDUsuario() {
        return IDUsuario;
    }
    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }
    public Date getFecha() {
        return Fecha;
    }
    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }
}
