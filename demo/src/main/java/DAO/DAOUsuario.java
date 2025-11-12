package DAO;
import java.util.List;
import DTO.Usuario;

public interface DAOUsuario {
    public void registarUsuario(Usuario Usu);
    public void actualizarUsuario(Usuario usu);
    public void eliminarUsuario(Usuario usu);
    public List<Usuario> listarUsuario();
    public Usuario buscarUsuario(Usuario usu);
}