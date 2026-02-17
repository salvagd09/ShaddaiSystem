/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

/**
 *
 * @author Usuario
 */
public class ResultadoAgregacion {
    private String estado;
    private String mensaje;
    private int IDPedido;
    public ResultadoAgregacion(String estado, String mensaje,int IDPedido) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.IDPedido=IDPedido;
    }
    public boolean esExitoso() {
        return "OK".equals(estado);
    }
    public int getIDPedido() {
        return IDPedido;
    }
}
