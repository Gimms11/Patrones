package service;

import java.util.List;

import DTO.TipoDocumento;
import DAO.DAOFactory;
import DAO.DAOTipoDocumento;

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
