/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class ValidarCantidadTiendaDTO {
    private boolean Exitoso;
    private String Mensaje;
    private String Nombre;
    private int  Cantidad;
    private double PrecioUnitario;
    private double Subtotal;
    private int Stock_Disponible;
    public ValidarCantidadTiendaDTO(boolean Exitoso, String Mensaje, String Nombre, int Cantidad, double PrecioUnitario, double Subtotal, int Stock_Disponible) {
        this.Exitoso = Exitoso;
        this.Mensaje = Mensaje;
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
        this.PrecioUnitario = PrecioUnitario;
        this.Subtotal = Subtotal;
        this.Stock_Disponible = Stock_Disponible;
    }
    public ValidarCantidadTiendaDTO(String mensajeError){
        this.Exitoso=false;
        this.Mensaje=mensajeError;
        this.Nombre=null;
        this.Cantidad=0;
        this.PrecioUnitario=0.0;
        this.Subtotal=0.0;
        this.Stock_Disponible=0;
    }
    public boolean esExitoso() { return Exitoso; }
    public String getMensaje() { return Mensaje; }
    public String getNombre() {
        return Nombre;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public int getStock_Disponible() {
        return Stock_Disponible;
    }
    
}
