package Modelo.DAO;
import Modelo.Conexion.dbConexion;
import Modelo.Entidad.Cliente;
import Modelo.Enum.TipoCliente;
import java.sql.Connection;
public class ClienteDAO {
   public void RegistrarloComoFrecuente(Cliente cliente){
       dbConexion dbc=new dbConexion();
       Connection con = dbc.conectar();
       String sql="{CALL Registrar_Cliente_Frecuente(?,?,?,?,?)}";
   }
   public void MostrarDatosClientes(TipoCliente tCliente,String DNI){
       String sql="{CALL Registrar_Cliente_Frecuente(?,?,?)}";
   }
}
