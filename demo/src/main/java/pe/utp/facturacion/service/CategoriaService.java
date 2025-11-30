package pe.utp.facturacion.service;

import pe.utp.facturacion.model.CategoriaProductos;
import pe.utp.facturacion.persistence.dao.DAOCategoriaProducto;
import pe.utp.facturacion.persistence.dao.DAOFactory;

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