/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

import Modelo.Enum.EstadoCliente;
import Modelo.Enum.TipoCliente;

/**
 *
 * @author Usuario
 */
public class Cliente {
    private int IDCliente;
    private String DNI;
    private String NombreCompleto;
    private String Telefono;
    private String Direccion;
    private String Correo;
    private EstadoCliente cliente;
    private TipoCliente tipoCliente;
    public int getIDCliente() {
        return IDCliente;
    }
    public EstadoCliente getCliente() {
        return cliente;
    }
    public void setCliente(EstadoCliente cliente) {
        this.cliente = cliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
    
    public void setIDCliente(int IDCliente) {
        this.IDCliente = IDCliente;
    }
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    public String getNombreCompleto() {
        return NombreCompleto;
    }
    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    public String getCorreo() {
        return Correo;
    }
    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }
    
}
