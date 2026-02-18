/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DAO;

import Modelo.Entidad.Notificacion;

/**
 *
 * @author Usuario
 */
public class NotificacionDAO {
    public void RegistrarNotificacion(){
        
    }
    public void MostrarNotificaciones(){
        String sql="{CALL Mostrar_Notificaciones()}";
    }
    public void MostrarListaProductosStockBajo(int IDNotificacion){
        String sql="CALL {Mostrar_Lista_ProductosBajoStock(IN P_ID_Notificacion INT)}";
    }
    public void NotificacionLeida(int IDNotificacion){
       String sql="CALL {Notificacion_Leida(?)}";
    }
}
