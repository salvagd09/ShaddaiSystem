/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class ResultadoOperacionDTO{
    private String estado;
    private String mensaje;
      private String nombreProducto;
    private double precio;
    private double subtotal;
    public ResultadoOperacionDTO(String estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }
    public ResultadoOperacionDTO() {
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public boolean esExitoso() {
        return "OK".equals(estado);
    }
    public String getNombreProducto() {
        return nombreProducto;
    }
    public double getPrecio() {
        return precio;
    }
    public double getSubtotal() {
        return subtotal;
    } 
    public String getEstado() { return estado; }
    public String getMensaje() { return mensaje; }
}
