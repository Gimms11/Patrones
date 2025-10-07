package repository;

import DTO.TipoDocumento;
import java.util.List;

public interface TipoDocumentoRepository {
    List<TipoDocumento> obtenerTodos();
}