package DAO;

import java.util.List;
import DTO.Cliente;

public interface DAOFiltrarClientes {
    List<Cliente> filtrarPorDocumento(String numDocumento);
    List<Cliente> filtrarPorNombre(String nombre);
    List<Cliente> filtrarPorUbicacion(Long idDistrito);
    List<Cliente> filtrarPorMultiplesCriterios(String numDocumento, String nombre, Long idDistrito);
}