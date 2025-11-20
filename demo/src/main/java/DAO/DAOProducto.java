package DAO;
import java.sql.SQLException;
import java.util.List;
import DTO.Producto;

public interface DAOProducto {
    public void registarProducto(Producto produc) throws SQLException;
    public void actualizarProducto(Producto produc);
    public void eliminarProducto(Producto produc);
    public List<Producto> listarProducto();
    public Producto buscarProducto(Long idProducto);
    List<Producto> filtrarProductos(
            String nombre,
            Double precio,
            Integer stock,
            String unidadMedida,
            Integer idCategoria,
            Integer idAfectacion) throws Exception;
}