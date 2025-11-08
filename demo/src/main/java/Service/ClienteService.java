package service;

import java.util.List;

import DTO.Cliente;
import DAO.DAOCliente;
import DAO.DAOFactory;

public class ClienteService {
    private final DAOCliente repository;
    
    public ClienteService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getClienteDAO();
    }

    public List<Cliente> obtenerTodos() {
        return repository.listarClientes();
    }

    public void insertarCliente(Cliente cliente){
        repository.registrarCliente(cliente);
    }

    public List<Cliente> filtrarClientes(String numDocumento, String nombres, String apellidos, Integer idDistrito,
            Integer idDocumento) {
        try {
            List<Cliente> productos = repository.filtrarClientes(numDocumento, nombres, apellidos, idDistrito, idDocumento);
            return productos != null ? productos : java.util.Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener Clientes: " + e.getMessage());
        }
    }

    public Cliente obtenerPorId(Long id) {
        return repository.buscarCliente(id);
    }
}
