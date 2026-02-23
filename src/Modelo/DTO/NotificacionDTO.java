/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class NotificacionDTO {
    private int IDNotificacion;
    private String Titulo;
    private String Mensaje;
    private Date Fecha;
    public NotificacionDTO(int IDNotificacion, String Titulo, String Mensaje, Date Fecha) {
        this.IDNotificacion = IDNotificacion;
        this.Titulo = Titulo;
        this.Mensaje = Mensaje;
        this.Fecha = Fecha;
    }
    public int getIDNotificacion() {
        return IDNotificacion;
    }
    public String getTitulo() {
        return Titulo;
    }
    public String getMensaje() {
        return Mensaje;
    }
    public Date getFecha() {
        return Fecha;
    }
}
