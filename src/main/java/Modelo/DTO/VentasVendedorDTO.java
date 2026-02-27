/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class VentasVendedorDTO {
    private String vendedor;
    private int cantidadVentas;
    private double montoTotal;
    public VentasVendedorDTO(String vendedor, int cantidadVentas, double montoTotal) {
        this.vendedor = vendedor;
        this.cantidadVentas = cantidadVentas;
        this.montoTotal = montoTotal;
    }
    public String getVendedor() {
        return vendedor;
    }
    public int getCantidadVentas() {
        return cantidadVentas;
    }
    public double getMontoTotal() {
        return montoTotal;
    }
}
