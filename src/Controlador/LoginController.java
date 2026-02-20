/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.EmpleadoDAO;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class LoginController {
    private EmpleadoDAO empleadoDAO=new EmpleadoDAO();
    public Map<Integer,String> AutenticarUsuario(String username,String contrasena){
        return empleadoDAO.LoginPedido(username,contrasena);
    }
}
