/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

public class Producto {
    private int IDProducto;
    private String CodigoProducto;
    private String nombre;
    private int StockTienda;
    private int StockAlmacen;
    private String UnidadMedida;
    private double Precio;
    private int StockMinimo;
    private int IDCategoria;
    public Producto(int IDProducto, String CodigoProducto, String nombre, int StockTienda, int StockAlmacen, String UnidadMedida, double Precio, int StockMinimo, int IDCategoria) {
        this.IDProducto = IDProducto;
        this.CodigoProducto = CodigoProducto;
        this.nombre = nombre;
        this.StockTienda = StockTienda;
        this.StockAlmacen = StockAlmacen;
        this.UnidadMedida = UnidadMedida;
        this.Precio = Precio;
        this.StockMinimo = StockMinimo;
        this.IDCategoria = IDCategoria;
    }
    
}
