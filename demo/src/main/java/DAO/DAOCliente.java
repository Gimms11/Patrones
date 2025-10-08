package DAO;

import java.util.List;
import DTO.Cliente;

public interface DAOCliente {
    void registrarCliente(Cliente cliente);
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(Cliente cliente);
    Cliente buscarCliente(Long idCliente);
    List<Cliente> listarClientes();
}
