/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.NotificacionDAO;
import Modelo.DTO.NotificacionDTO;
import Modelo.DTO.ProductosBajoStockDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class NotificacionesController {
    public NotificacionDAO notificacion;

    public NotificacionesController() {
        this.notificacion=new NotificacionDAO();
    }
    
    public List<NotificacionDTO> mostrarNotificaciones(){
        return notificacion.MostrarNotificaciones();
    }
    public Map<String,List<ProductosBajoStockDTO>> mostrarListaProductos(int IDNotificacion){
        return notificacion.MostrarListaProductosStockBajo(IDNotificacion);
    }
    public boolean NotificacionLeida(int IDNotificacion){
        return notificacion.NotificacionLeida(IDNotificacion);
    } 
}
