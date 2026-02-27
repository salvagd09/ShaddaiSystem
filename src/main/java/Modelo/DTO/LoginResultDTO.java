/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.DTO;

/**
 *
 * @author Usuario
 */
public class LoginResultDTO {
    private int idUsuario;
    private String nombreRol;
    public LoginResultDTO(int idUsuario, String nombreRol) {
        this.idUsuario = idUsuario;
        this.nombreRol = nombreRol;
    }
    public int getIdUsuario() { 
        return idUsuario; 
    }
    public String getNombreRol() { 
        return nombreRol; 
    }
    public boolean esAdministrador() { 
        return "Administrador".equalsIgnoreCase(nombreRol); 
    }
    public boolean esVendedor() { 
        return "Vendedor".equalsIgnoreCase(nombreRol); 
    }
    public boolean esAlmacenero() { 
        return "Almacenero".equalsIgnoreCase(nombreRol); 
    }
}
