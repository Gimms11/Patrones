package service;

import DTO.CategoriaProductos;
import DAO.DAOCategoriaProducto;
import java.util.List;

public class CategoriaService {

    private final DAOCategoriaProducto repository;

    public CategoriaService(DAOCategoriaProducto repository) {
        this.repository = repository;
    }

    public List<CategoriaProductos> listarCategorias() {
        return repository.obtenerTodas();
    }
}