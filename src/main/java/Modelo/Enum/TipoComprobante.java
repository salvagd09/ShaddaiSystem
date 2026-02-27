/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Enum;

/**
 *
 * @author Usuario
 */
public enum TipoComprobante {
    BOLETA("Boleta"),
    FACTURA("Factura");
    private String nombreTC;
    private TipoComprobante(String nombreTC) {
        this.nombreTC = nombreTC;
    }
    
}
