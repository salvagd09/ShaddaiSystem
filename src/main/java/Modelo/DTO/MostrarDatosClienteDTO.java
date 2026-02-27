/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class MostrarDatosClienteDTO {
    private boolean Exitoso;
    private String Mensaje;
    private String RUC;
    private String NombreCompleto;
    public MostrarDatosClienteDTO(boolean Exitoso, String Mensaje, String RUC, String NombreCompleto) {
        this.Exitoso = Exitoso;
        this.Mensaje = Mensaje;
        this.RUC = RUC;
        this.NombreCompleto = NombreCompleto;
    }
    public MostrarDatosClienteDTO(String mensajeError){
        this.Exitoso=false;
        this.Mensaje=mensajeError;
        this.RUC=null;
        this.NombreCompleto=null;
    }
     public boolean esExitoso() { return Exitoso; }

    public String getRUC() {
        return RUC;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }
     
}
