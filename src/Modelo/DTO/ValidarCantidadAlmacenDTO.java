/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class ValidarCantidadAlmacenDTO {
    private boolean Exitoso;
    private String Mensaje;
    private int IDProducto;
    private String Nombre;
    private int  Cantidad;
    private String UnidadMedida;
    public double SubtotalStock;
    public ValidarCantidadAlmacenDTO(boolean Exitoso, String Mensaje, String Nombre, int Cantidad, String UnidadMedida) {
        this.Exitoso = Exitoso;
        this.Mensaje = Mensaje;
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
        this.UnidadMedida = UnidadMedida;
    }

    public ValidarCantidadAlmacenDTO(boolean Exitoso,String Mensaje,int IDProducto, String Nombre, int Cantidad, String UnidadMedida, double SubtotalStock) {
        this.Exitoso=Exitoso;
        this.Mensaje=Mensaje;
        this.IDProducto = IDProducto;
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
        this.UnidadMedida = UnidadMedida;
        this.SubtotalStock = SubtotalStock;
    }
    
    public ValidarCantidadAlmacenDTO(String mensajeError){
        this.Exitoso=false;
        this.Mensaje=mensajeError;
        this.Nombre=null;
        this.Cantidad=0;
        this.UnidadMedida=null;
    }
    public boolean esExitoso() { return Exitoso; }
    public String getMensaje() { return Mensaje; }
    public String getNombre() {
        return Nombre;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public int getIDProducto() {
        return IDProducto;
    }

    public double getSubtotalStock() {
        return SubtotalStock;
    }
    
}
