/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class VentaCategoriaDTO {
    private String categoria;
    private double montoTotal;
    private int numVentas;
    public VentaCategoriaDTO(String categoria, double montoTotal, int numVentas) {
        this.categoria = categoria;
        this.montoTotal = montoTotal;
        this.numVentas = numVentas;
    }
    public String getCategoria() {
        return categoria;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public int getNumVentas() {
        return numVentas;
    }
    
}
