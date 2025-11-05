package DAO;

import java.util.List;
import DTO.Distrito;

public interface DAODistrito {
    List<Distrito> listarDistritos();
    Distrito obtenerDistrito(Long id);
    String obtenerNombreDistrito(Long id);
}