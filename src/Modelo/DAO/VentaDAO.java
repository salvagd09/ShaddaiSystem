/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Entidad.Categoria;
import Modelo.Entidad.Cliente;
import Modelo.Entidad.Empleado;
import Modelo.Entidad.Producto;
import Modelo.Entidad.Venta;
import Modelo.Enum.MetodoPago;
import Modelo.Enum.TipoComprobante;
import Modelo.Enum.TipoVenta;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class VentaDAO {
    public void GenerarReporteVenta(String nombreCategoria,String nombreVendedor,Date Fecha){
        String sql="{CALL SP_Generar_Reporte_Ventas(?,?,?)}";
    }
    public int RegistrarVenta(Cliente cliente,int idUsuario,TipoVenta tVenta,TipoComprobante tComprobante,
    MetodoPago mPago){
        String sql="{CALL Registrar_Venta_General(?,?,?,?,?,?,?,?,?,?)}";
        int idVentaGenerada=0;
        return idVentaGenerada;
    }
    public void RegistrarDetalleVenta(int idVentaGenerada,String codigoProducto,int Cantidad){
        String sql="{CALL Registrar_Detalle_Venta(?,?,?)}";
    }
    /*En la BD es Contabilizar_Compras_Mes pero en si representa las ventas que ha
    hecho un cliente en especifico*/ 
    public void ContabilizarVentasMes(String DNI,String RUC){
        String sql="{CALL Contabilizar_Compras_Mes(?,?)}";
    }
}
