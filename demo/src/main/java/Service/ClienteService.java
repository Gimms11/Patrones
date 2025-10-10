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
}
