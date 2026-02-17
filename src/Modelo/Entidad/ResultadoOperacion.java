/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

/**
 *
 * @author Usuario
 */
public class ResultadoOperacion{
    private String estado;
    private String mensaje;
    
    public ResultadoOperacion(String estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }
    
    public boolean esExitoso() {
        return "OK".equals(estado);
    }
    
    public String getEstado() { return estado; }
    public String getMensaje() { return mensaje; }
}
