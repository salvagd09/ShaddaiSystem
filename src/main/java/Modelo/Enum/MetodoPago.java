/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Enum;

/**
 *
 * @author Usuario
 */
public enum MetodoPago {
    YAPEPLIN("Yape/Plin"),
    EFECTIVO("Efectivo");
    private String nombreMetodoPago;
    private MetodoPago(String nombreMetodoPago) {
        this.nombreMetodoPago = nombreMetodoPago;
    }

    
}
