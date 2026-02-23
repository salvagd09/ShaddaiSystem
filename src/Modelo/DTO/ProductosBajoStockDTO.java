/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class ProductosBajoStockDTO {
    private String Titulo;
    private String Nombre;
    private int StockActual;
    private int StockMinimo;
    public ProductosBajoStockDTO(String Titulo, String Nombre, int StockActual, int StockMinimo) {
        this.Titulo = Titulo;
        this.Nombre = Nombre;
        this.StockActual = StockActual;
        this.StockMinimo = StockMinimo;
    }
    public String getTitulo() {
        return Titulo;
    }
    public String getNombre() {
        return Nombre;
    }
    public int getStockActual() {
        return StockActual;
    }
    public int getStockMinimo() {
        return StockMinimo;
    } 
}
