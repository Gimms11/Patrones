package pe.utp.facturacion.persistence.dao;

import pe.utp.facturacion.model.CategoriaProductos;
import java.util.List;

public interface DAOCategoriaProducto {
    List<CategoriaProductos> obtenerTodas();
    CategoriaProductos obtener(Long id);
}