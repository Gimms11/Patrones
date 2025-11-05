package DAO;

import DTO.CategoriaProductos;
import java.util.List;

public interface DAOCategoriaProducto {
    List<CategoriaProductos> obtenerTodas();
    CategoriaProductos obtener(Long id);
}