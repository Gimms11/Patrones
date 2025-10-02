package DAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.ConexionBD;
import DAO.DAOCliente;
import DTO.Cliente;

public class DAOClienteImpl implements DAOCliente{

    private static final String SQLregistrar="Insert into cliente (nombres, apellidos, telefono,correo, direccion, numDocumento,idDistrito,idDocumento) "+
                                                "values(?,?,?,?,?,?,?,?)";
    private static final String SQLeliminar="Delete from cliente where idCliente = ?";
    private static final String SQLactualizar="Update cliente set nombres = ?, apellidos = ?, telefono = ?, correo = ?, direccion = ?, numDocumento = ?,idDistrito = ?, idDocumento = ?  "+
                                            "where idCliente = ?";
    private static final String SQLbuscar="Select * from cliente where idCliente = ?";
    private static final String SQLlista="Select * from cliente";
    
    @Override
    public void registarCliente(Cliente clnt) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLregistrar);
            ps.setString(1, clnt.getNombre());
            ps.setString(2, clnt.getApellidos());
            ps.setString(3, clnt.getTelefono());
            ps.setString(4, clnt.getCorreo());
            ps.setString(5, clnt.getDireccion());
            ps.setString(6, clnt.getNumeroDocumento());
            ps.setLong(7, clnt.getIdDistrito());
            ps.setLong(8, clnt.getIdDocumento());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarCliente(Cliente clnt) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLactualizar);
            ps.setString(1, clnt.getNombre());
            ps.setString(2, clnt.getApellidos());
            ps.setString(3, clnt.getTelefono());
            ps.setString(4, clnt.getCorreo());
            ps.setString(5, clnt.getDireccion());
            ps.setString(6, clnt.getNumeroDocumento());
            ps.setLong(7, clnt.getIdDistrito());
            ps.setLong(8, clnt.getIdDocumento());
            ps.setLong(9, clnt.getIdCliente());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCliente(Cliente clnt) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLeliminar);
            ps.setLong(1, clnt.getIdCliente());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listarCliente() {
        List<Cliente> listaCliente=new ArrayList<>();
        try{     
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLlista);
            ResultSet res=ps.executeQuery();            
            while (res.next()) {
                Cliente c = new Cliente(
                res.getLong("idCliente"),
                res.getString("nombres"),
                res.getString("apellidos"),
                res.getString("telefono"),
                res.getString("correo"),
                res.getString("direccion"),
                res.getString("numDocumento"),
                res.getLong("idDistrito"),
                res.getLong("idDocumento"));
                listaCliente.add(c);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listaCliente;
    }

    @Override
    public Cliente buscarCliente(Object key) {
        Cliente c=null;
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLbuscar);
            ps.setLong(1, Long.parseLong(key.toString()));;
            ResultSet res=ps.executeQuery();
            if (res.next()) {
                c = new Cliente(
                res.getLong("idCliente"),
                res.getString("nombres"),
                res.getString("apellidos"),
                res.getString("telefono"),
                res.getString("correo"),
                res.getString("direccion"),
                res.getString("numDocumento"),
                res.getLong("idDistrito"),
                res.getLong("idDocumento"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }
    
}
