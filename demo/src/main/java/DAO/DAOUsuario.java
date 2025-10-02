package DAO;
import java.util.List;
import DTO.Usuario;

public interface DAOUsuario {
    public void registarProducto(Usuario Usu);
    public void actualizarProducto(Usuario usu);
    public void eliminarProducto(Usuario usu);
    public List<Usuario> listarProducto();
    public Usuario buscarProducto(Object key);
}