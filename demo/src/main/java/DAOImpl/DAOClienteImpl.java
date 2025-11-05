package DAOImpl;

import DAO.ConexionBD;
import DAO.DAOCliente;
import DTO.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOClienteImpl implements DAOCliente {

    private static final String SQL_INSERT = 
        "INSERT INTO cliente (nombres, apellidos, telefono, correo, direccion, numdocumento, iddistrito, iddocumento) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = 
        "UPDATE cliente SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, direccion = ?, " +
        "numdocumento = ?, iddistrito = ?, iddocumento = ? WHERE idcliente = ?";

    private static final String SQL_DELETE = 
        "DELETE FROM cliente WHERE idcliente = ?";

    private static final String SQL_SELECT_BY_ID = 
        "SELECT * FROM cliente WHERE idcliente = ?";

    private static final String SQL_SELECT_ALL = 
        "SELECT c.*, d.nombre AS nombreDistrito, t.nombre AS nombreTipoDocumento " +
        "FROM cliente c " +
        "INNER JOIN distrito d ON d.iddistrito = c.iddistrito " +
        "INNER JOIN tipodocumento t ON t.iddocumento = c.iddocumento";

    @Override
    public void registrarCliente(Cliente cliente) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            ps.setString(1, cliente.getNombres());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getNumDocumento());
            ps.setLong(7, cliente.getIdDistrito());
            ps.setLong(8, cliente.getIdDocumento());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, cliente.getNombres());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getNumDocumento());
            ps.setLong(7, cliente.getIdDistrito());
            ps.setLong(8, cliente.getIdDocumento());
            ps.setLong(9, cliente.getIdCliente());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setLong(1, cliente.getIdCliente());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    @Override
    public Cliente buscarCliente(Long idCliente) {
        Cliente cliente = null;
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setLong(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente(
                        rs.getLong("idcliente"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("direccion"),
                        rs.getString("numdocumento"),
                        rs.getLong("iddistrito"),
                        rs.getLong("iddocumento"),
                        rs.getString("nombreDistrito"),
                        rs.getString("nombreTipoDocumento")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        }
        return cliente;
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getLong("idcliente"),
                    rs.getString("nombres"),
                    rs.getString("apellidos"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("direccion"),
                    rs.getString("numdocumento"),
                    rs.getLong("iddistrito"),
                    rs.getLong("iddocumento"),
                    rs.getString("nombredistrito"),
                    rs.getString("nombretipodocumento")
                );
                cliente.setNombreDistrito(rs.getString("nombredistrito"));
                cliente.setNombreTipoDocumento(rs.getString("nombretipodocumento"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }
}
