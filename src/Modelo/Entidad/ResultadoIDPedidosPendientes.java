/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ResultadoIDPedidosPendientes {
    private boolean exitoso;
    private String mensaje;
    private List<Integer> idsPedidos;
     public ResultadoIDPedidosPendientes(boolean exitoso, String mensaje, List<Integer> ids) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.idsPedidos = ids != null ? ids : new ArrayList<>();
    }
    
    // Constructor para errores
    public ResultadoIDPedidosPendientes(String mensajeError) {
        this.exitoso = false;
        this.mensaje = mensajeError;
        this.idsPedidos = new ArrayList<>();
    }
    
    public boolean esExitoso() { return exitoso; }
    public String getMensaje() { return mensaje; }
    public List<Integer> getIdsPedidos() { return idsPedidos; }
    public boolean tienePedidos() { return !idsPedidos.isEmpty(); }
}
