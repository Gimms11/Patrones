package DAO;

import DTO.TipoDocumento;
import java.util.List;

public interface DAOTipoDocumento {
    List<TipoDocumento> obtenerTodos();
    TipoDocumento obtener(Long id);
}