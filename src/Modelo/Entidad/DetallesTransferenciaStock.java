
package Modelo.Entidad;

public class DetallesTransferenciaStock {
   private int IDDetalleTrans;
   private int IDTransferencia;
   private int IDProducto;
   private int Cantidad;

    public int getIDDetalleTrans() {
        return IDDetalleTrans;
    }

    public void setIDDetalleTrans(int IDDetalleTrans) {
        this.IDDetalleTrans = IDDetalleTrans;
    }

    public int getIDTransferencia() {
        return IDTransferencia;
    }

    public void setIDTransferencia(int IDTransferencia) {
        this.IDTransferencia = IDTransferencia;
    }

    public int getIDProducto() {
        return IDProducto;
    }

    public void setIDProducto(int IDProducto) {
        this.IDProducto = IDProducto;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
   
}
