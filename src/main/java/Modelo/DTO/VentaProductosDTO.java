/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class VentaProductosDTO {
    private String nombre;
    private String categoria;
    private int totalVendido;
    private double montoTotal;
    public VentaProductosDTO(String nombre, String categoria, int totalVendido, double montoTotal) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.totalVendido = totalVendido;
        this.montoTotal = montoTotal;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCategoria() {
        return categoria;
    }
    public int getTotalVendido() {
        return totalVendido;
    }
    public double getMontoTotal() {
        return montoTotal;
    }
}
