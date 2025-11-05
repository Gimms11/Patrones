package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOFiltrarClientes;
import DTO.Cliente;

public class DAOFiltrarClientesImpl implements DAOFiltrarClientes {

    private static final String SQL_FILTRAR_DOCUMENTO = 
        "SELECT * FROM cliente WHERE numdocumento LIKE ?";
    
    private static final String SQL_FILTRAR_NOMBRE = 
        "SELECT * FROM cliente WHERE LOWER(nombres) LIKE LOWER(?) OR LOWER(apellidos) LIKE LOWER(?)";
    
    private static final String SQL_FILTRAR_UBICACION = 
        "SELECT * FROM cliente WHERE iddistrito = ?";
    
    private static final String SQL_FILTRAR_MULTIPLE = 
        "SELECT c.*, d.nombre as distrito_nombre, td.nombre as tipodoc_nombre " +
        "FROM cliente c " +
        "LEFT JOIN distrito d ON c.iddistrito = d.iddistrito " +
        "LEFT JOIN tipodocumento td ON c.iddocumento = td.iddocumento " +
        "WHERE " +
        "(?::varchar IS NULL OR c.numdocumento LIKE ?) AND " +
        "(?::varchar IS NULL OR LOWER(c.nombres) LIKE LOWER(?) OR LOWER(c.apellidos) LIKE LOWER(?)) AND " +
        "(?::bigint IS NULL OR c.iddistrito = ?) " +
        "ORDER BY c.apellidos, c.nombres";

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getLong("idcliente"));
        cliente.setNombres(rs.getString("nombres"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setNumDocumento(rs.getString("numdocumento"));
        cliente.setIdDistrito(rs.getLong("iddistrito"));
        cliente.setIdDocumento(rs.getLong("iddocumento"));
        
        // Agregar los nombres de distrito y tipo de documento
        cliente.setNombreDistrito(rs.getString("distrito_nombre"));
        cliente.setNombreTipoDocumento(rs.getString("tipodoc_nombre"));
        
        return cliente;
    }

    @Override
    public List<Cliente> filtrarPorDocumento(String numDocumento) {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FILTRAR_DOCUMENTO)) {
            
            ps.setString(1, "%" + numDocumento + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public List<Cliente> filtrarPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FILTRAR_NOMBRE)) {
            
            String paramBusqueda = "%" + nombre + "%";
            ps.setString(1, paramBusqueda);
            ps.setString(2, paramBusqueda);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public List<Cliente> filtrarPorUbicacion(Long idDistrito) {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FILTRAR_UBICACION)) {
            
            ps.setLong(1, idDistrito);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public List<Cliente> filtrarPorMultiplesCriterios(String numDocumento, String nombre, Long idDistrito) {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FILTRAR_MULTIPLE)) {
            
            // Parámetros para documento
            ps.setString(1, numDocumento);
            ps.setString(2, numDocumento != null ? "%" + numDocumento + "%" : "%");
            
            // Parámetros para nombre
            ps.setString(3, nombre);
            ps.setString(4, nombre != null ? "%" + nombre + "%" : "%");
            ps.setString(5, nombre != null ? "%" + nombre + "%" : "%");
            
            // Parámetros para distrito
            ps.setObject(6, idDistrito);
            ps.setObject(7, idDistrito);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}