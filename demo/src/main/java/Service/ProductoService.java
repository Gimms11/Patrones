package service;

import java.util.List;

import DTO.Producto;
import DAO.DAOProducto;
import DAO.DAOFactory;

public class ProductoService {
    private final DAOProducto repository;
    
    public ProductoService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getProductoDAO();
    }

    public List<Producto> obtenerTodos() {
        return repository.listarProducto();
    }

}
