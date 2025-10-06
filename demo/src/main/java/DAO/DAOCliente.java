package DAO;
import java.util.List;
import DTO.Cliente;

public interface DAOCliente {
    public void registarCliente(Cliente clnt);
    public void actualizarCliente (Cliente clnt);
    public void eliminarCliente(Cliente clnt);
    public List<Cliente> listarCliente();
    public Cliente buscarCliente(Cliente clnt);
}