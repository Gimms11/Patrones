package pe.utp.facturacion.persistence.dao;

import pe.utp.facturacion.model.TipoDocumento;
import java.util.List;

public interface DAOTipoDocumento {
    List<TipoDocumento> obtenerTodos();
    TipoDocumento obtener(Long id);
}