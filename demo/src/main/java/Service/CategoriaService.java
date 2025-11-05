package service;

import DTO.CategoriaProductos;
import DAO.DAOCategoriaProducto;
import DAO.DAOFactory;

import java.util.List;

public class CategoriaService {

    private final DAOCategoriaProducto repository;

    public CategoriaService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getCategoriaProductoDAO();
    }

    public List<CategoriaProductos> listarCategorias() {
        return repository.obtenerTodas();
    }

    public CategoriaProductos obtenerCategoria(Long id) {
        if (id == null) return null;
        return repository.obtener(id);
    }
}