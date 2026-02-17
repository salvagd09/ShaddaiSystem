/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Conexion;
import java.sql.*;
public class dbConexion {
    static String url="jdbc:mysql://localhost:3306/shaddaiSystem";
    static String username="root";
    static String password="MinuevaContra1";
    public static Connection conectar(){
        Connection con=null;
        try{
            con=DriverManager.getConnection(url,username,password);
            System.out.println("Conexion exitosa");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error de conexion");
        }
        return con;
    }
}
