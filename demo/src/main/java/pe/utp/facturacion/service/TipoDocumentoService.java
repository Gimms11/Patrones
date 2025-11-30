package pe.utp.facturacion.service;

import java.util.List;

import pe.utp.facturacion.model.TipoDocumento;
import pe.utp.facturacion.persistence.dao.DAOFactory;
import pe.utp.facturacion.persistence.dao.DAOTipoDocumento;

public class TipoDocumentoService {
    private final DAOTipoDocumento repository;

    public TipoDocumentoService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getTipoDocumentoDAO();
    }

    public List<TipoDocumento> cargarTipoDocumentos() {
        return repository.obtenerTodos();
    }

    public TipoDocumento obtenerTipoDocumento(Long id) {
        if (id == null) return null;
        return repository.obtener(id);
    }
}
