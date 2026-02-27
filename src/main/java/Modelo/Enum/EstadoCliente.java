/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Enum;

/**
 *
 * @author Usuario
 */
public enum EstadoCliente {
    FRECUENTE("Frecuente"),
    NOFRECUENTE("No Frecuente");
    private String nombreEstadoC;
    private EstadoCliente(String nombreEstadoC) {
        this.nombreEstadoC = nombreEstadoC;
    }
    
}
