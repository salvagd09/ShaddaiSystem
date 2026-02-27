/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

import Modelo.Enum.EstadoNotificacion;
import java.util.Date;

/**
 *
 * @author Usuario
 */
public class Notificacion {
    private int IDNotificacion;
    private String Titulo;
    private String Mensaje;
    private Date Fecha;
    private EstadoNotificacion EstadoNoti;
    public int getIDNotificacion() {
        return IDNotificacion;
    }
    public void setIDNotificacion(int IDNotificacion) {
        this.IDNotificacion = IDNotificacion;
    }
    public String getTitulo() {
        return Titulo;
    }
    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }
    public String getMensaje() {
        return Mensaje;
    }
    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }
    public Date getFecha() {
        return Fecha;
    }
    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }
    public EstadoNotificacion getEstadoNoti() {
        return EstadoNoti;
    }
    public void setEstadoNoti(EstadoNotificacion EstadoNoti) {
        this.EstadoNoti = EstadoNoti;
    }
}
