/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.NotificacionDAO;
import Modelo.DTO.NotificacionDTO;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class NotificacionesController {
    public NotificacionDAO notificacion;
    public List<NotificacionDTO> Mostrar_Notificacion(){
        return notificacion.MostrarNotificaciones();
    }
}
