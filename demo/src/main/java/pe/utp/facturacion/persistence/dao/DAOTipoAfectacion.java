package pe.utp.facturacion.persistence.dao;

import pe.utp.facturacion.model.AfectacionProductos;
import java.util.List;

public interface DAOTipoAfectacion {
    List<AfectacionProductos> obtenerTodas();
    AfectacionProductos obtener(Long id);
}