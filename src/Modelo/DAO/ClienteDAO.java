package Modelo.DAO;
import Modelo.Conexion.dbConexion;
import Modelo.Entidad.Cliente;
import java.sql.Connection;
public class ClienteDAO {
   public void RegistrarloComoFrecuente(Cliente cliente){
       dbConexion dbc=new dbConexion();
       Connection con = dbc.conectar();
   }
   public void MostrarDatosClientes(Cliente cliente){
       
   }
}
