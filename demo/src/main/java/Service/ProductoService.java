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
        try {
            List<Producto> productos = repository.listarProducto();
            return productos != null ? productos : java.util.Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener productos: " + e.getMessage());
        }
    }

    public void registrarProducto(Producto produc) {
        try {
            repository.registarProducto(produc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al registrar producto: " + e.getMessage());
        }
    }
    

    public List<Producto> filtrarProductos(String nombre, Double precio, Integer stock, String unidad,
    Integer idAfectacion, Integer idCategoria) {
        try {
            List<Producto> productos = repository.filtrarProductos(nombre, precio, stock, unidad, idCategoria, idAfectacion);
            return productos != null ? productos : java.util.Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener productos: " + e.getMessage());
        }
    }

}
