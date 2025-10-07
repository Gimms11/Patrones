package service;

import DTO.TipoDocumento;
import DAO.DAOTipoDocumento;
import java.util.List;

public class TipoDocumentoService {
    private final DAOTipoDocumento repository;

    public TipoDocumentoService(DAOTipoDocumento repository) {
        this.repository = repository;
    }

    public List<TipoDocumento> cargarTipoDocumentos() {
        return repository.obtenerTodos();
    }
}
