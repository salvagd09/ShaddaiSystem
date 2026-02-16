/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Enum;

/**
 *
 * @author Usuario
 */
public enum TipoCliente {
    PERSONA("Persona"),
    EMPRESA("Empresa");
    private String nombreTC;
    private TipoCliente(String nombreTC) {
        this.nombreTC = nombreTC;
    }
}
