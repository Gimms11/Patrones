package pe.utp.facturacion.persistence.dao;

import java.util.List;
import pe.utp.facturacion.model.Distrito;

public interface DAODistrito {
    List<Distrito> listarDistritos();
    Distrito obtenerDistrito(Long id);
    String obtenerNombreDistrito(Long id);
}