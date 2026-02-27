/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Enum;

/**
 *
 * @author Usuario
 */
public enum EstadoPedido {
    PENDIENTE("Pendiente"),
    CONFIRMADO("Confirmado"),
    FINALIZADO("Finalizado");
    private String nombreEstadoP;
    private EstadoPedido(String nombreEstadoP) {
        this.nombreEstadoP = nombreEstadoP;
    }
}
