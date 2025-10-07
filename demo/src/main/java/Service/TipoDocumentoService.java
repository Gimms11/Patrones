package service;

import DTO.TipoDocumento;
import repository.TipoDocumentoRepository;
import java.util.List;

public class TipoDocumentoService {
    private final TipoDocumentoRepository repository;

    public TipoDocumentoService(TipoDocumentoRepository repository) {
        this.repository = repository;
    }

    public List<TipoDocumento> cargarTipoDocumentos() {
        return repository.obtenerTodos();
    }
}
