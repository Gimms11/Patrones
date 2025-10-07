package DAO;
import java.util.List;
import DTO.Producto;

public interface DAOProducto {
    public void registarProducto(Producto produc);
    public void actualizarProducto(Producto produc);
    public void eliminarProducto(Producto produc);
    public List<Producto> listarProducto();
    public Producto buscarProducto(Producto produc);
}