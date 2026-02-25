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

    public Producto() {
    }

    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public String getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(String CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStockTienda() {
        return StockTienda;
    }

    public void setStockTienda(int StockTienda) {
        this.StockTienda = StockTienda;
    }

    public int getStockAlmacen() {
        return StockAlmacen;
    }

    public void setStockAlmacen(int StockAlmacen) {
        this.StockAlmacen = StockAlmacen;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String UnidadMedida) {
        this.UnidadMedida = UnidadMedida;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public int getStockMinimo() {
        return StockMinimo;
    }

    public void setStockMinimo(int StockMinimo) {
        this.StockMinimo = StockMinimo;
    }

    public int getIDCategoria() {
        return IDCategoria;
    }

    public void setIDCategoria(int IDCategoria) {
        this.IDCategoria = IDCategoria;
    }
    
    
}
