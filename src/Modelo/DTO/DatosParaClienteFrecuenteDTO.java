/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class DatosParaClienteFrecuenteDTO {
    private String NombreCliente;
    private String mensaje;
    private int IDVenta;
    private Date Fecha;
    private int TotalCompraMes;
    public DatosParaClienteFrecuenteDTO(String NombreCliente, int IDVenta, Date Fecha, int TotalCompraMes) {
        this.NombreCliente = NombreCliente;
        this.IDVenta = IDVenta;
        this.Fecha = Fecha;
        this.TotalCompraMes = TotalCompraMes;
    }
    public DatosParaClienteFrecuenteDTO(String mensajeError){
        this.mensaje=mensajeError;
        this.NombreCliente=null;
        this.IDVenta=0;
        this.Fecha=null;
        this.TotalCompraMes=0;
    }
    public String getNombreCliente() {
        return NombreCliente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getIDVenta() {
        return IDVenta;
    }

    public Date getFecha() {
        return Fecha;
    }

    public int getTotalCompraMes() {
        return TotalCompraMes;
    }
    
}
