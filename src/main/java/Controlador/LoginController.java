/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.EmpleadoDAO;
import Modelo.DTO.LoginResultDTO;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class LoginController {
    private EmpleadoDAO empleadoDAO=new EmpleadoDAO();
    public LoginResultDTO AutenticarUsuario(String username,String contrasena){
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        if (contrasena == null || contrasena.isEmpty()) {
            return null;
        }
        return empleadoDAO.LoginPedido(username,contrasena);
    }
}
