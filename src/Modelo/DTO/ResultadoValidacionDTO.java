/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class ResultadoValidacionDTO {
    private boolean Exitoso;
    private String Mensaje;
    private String Nombre;
    private int  Cantidad;
    private String UnidadMedida;
    public ResultadoValidacionDTO(boolean Exitoso, String Mensaje, String Nombre, int Cantidad, String UnidadMedida) {
        this.Exitoso = Exitoso;
        this.Mensaje = Mensaje;
        this.Nombre = Nombre;
        this.Cantidad = Cantidad;
        this.UnidadMedida = UnidadMedida;
    }
    public ResultadoValidacionDTO(String mensajeError){
        this.Exitoso=false;
        this.Mensaje=mensajeError;
        this.Nombre=null;
        this.Cantidad=0;
        this.UnidadMedida=null;
    }
    public boolean esExitoso() { return Exitoso; }
    public String getMensaje() { return Mensaje; }
    public boolean isExitoso() {
        return Exitoso;
    }
    public String getNombre() {
        return Nombre;
    }
    public int getCantidad() {
        return Cantidad;
    }
    public String getUnidadMedida() {
        return UnidadMedida;
    }
}
