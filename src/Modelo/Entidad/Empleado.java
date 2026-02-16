/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidad;

/**
 *
 * @author Usuario
 */
public class Empleado {
    public int IDUsuario;
    public String Nombres;
    public String Apellidos;
    public String Contrasena;
    public int IDRol;
    public String Username;
    public int getIDUsuario() {
        return IDUsuario;
    }
    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }
    public String getNombres() {
        return Nombres;
    }
    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }
    public String getApellidos() {
        return Apellidos;
    }
    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }
    public String getContrasena() {
        return Contrasena;
    }
    public void setContrasena(String Contrasena) {
        this.Contrasena = Contrasena;
    }
    public int getIDRol() {
        return IDRol;
    }
    public void setIDRol(int IDRol) {
        this.IDRol = IDRol;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String Username) {
        this.Username = Username;
    }
}
