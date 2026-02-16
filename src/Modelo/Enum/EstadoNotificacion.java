/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Modelo.Enum;

/**
 *
 * @author Usuario
 */
public enum EstadoNotificacion {
    LEIDO("Leido"),
    NOLEIDO("No Leido");
    private String nombreEstadoN;
    private EstadoNotificacion(String nombreEstadoN) {
        this.nombreEstadoN = nombreEstadoN;
    }
    
}
