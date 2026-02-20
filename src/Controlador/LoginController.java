/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.DAO.EmpleadoDAO;

/**
 *
 * @author Usuario
 */
public class LoginController {
    private EmpleadoDAO empleadoDAO=new EmpleadoDAO();
    public void AutenticarUsuario(String username,String contrasena){
        empleadoDAO.LoginPedido(username,contrasena);
    }
}
