package DAO;

import java.sql.SQLException;
import java.util.List;
import DTO.Cliente;

public interface DAOCliente {
    void registrarCliente(Cliente cliente);
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(Cliente cliente);
    Cliente buscarCliente(Long idCliente);
    List<Cliente> listarClientes();
    List<Cliente> filtrarClientes(String numDocumento, String nombres, String apellidos, Integer idDistrito,
            Integer idDocumento)
            throws SQLException;
}
