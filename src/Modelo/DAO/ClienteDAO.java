package Modelo.DAO;
import Modelo.Conexion.dbConexion;
import Modelo.DTO.MostrarDatosClienteDTO;
import Modelo.Entidad.Cliente;
import Modelo.Enum.TipoCliente;
import com.mysql.cj.jdbc.CallableStatement;
import java.sql.*;
import java.sql.Types;
public class ClienteDAO {
   public boolean RegistrarloComoFrecuente(Cliente cliente){
       dbConexion dbc=new dbConexion();
       Connection con = dbc.conectar();
       String sql="{CALL Registrar_Cliente_Frecuente(?,?,?,?,?)}";
       try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
            stmt.setString(1, cliente.getDNI());
            if(cliente.getRUC()!=null && !cliente.getRUC().isEmpty()){
                stmt.setString(2,cliente.getRUC());
            }
            else{
                stmt.setNull(2, Types.VARCHAR);
            }
            stmt.setString(3,cliente.getTelefono());
            stmt.setString(4,cliente.getCorreo());
            stmt.setString(5,cliente.getDireccion());
            stmt.execute();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false; 
        }
   }
   public MostrarDatosClienteDTO MostrarDatosClientes(TipoCliente tCliente,String DNI){
       String sql="{CALL Registrar_Cliente_Frecuente(?,?)}";
       try (Connection conn = new dbConexion().conectar();
        CallableStatement stmt = (CallableStatement) conn.prepareCall(sql)){
           stmt.setString(1, tCliente.name());
           stmt.setString(2,DNI);
           ResultSet rs=stmt.executeQuery();
           if(rs.next()){
               String estado=rs.getString("Estado");
               if("OK".equals("Estado")){
                   return new MostrarDatosClienteDTO(true,rs.getString("Mensaje"),rs.getString("RUC"),rs.getString("NombreCompleto"));
               }               
           }
       }catch(SQLException e){
            e.printStackTrace();
            return new MostrarDatosClienteDTO("Error en la BD:"+e.getMessage());
        }
       return new MostrarDatosClienteDTO("Error en el servidor");
   }
}
