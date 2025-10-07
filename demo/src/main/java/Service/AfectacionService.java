package service;

import DTO.AfectacionProductos;
import DAO.DAOTipoAfectacion;
import java.util.List;

public class AfectacionService {

    private final DAOTipoAfectacion repository;

    public AfectacionService(DAOTipoAfectacion repository) {
        this.repository = repository;
    }

    public List<AfectacionProductos> listarAfectaciones() {
        return repository.obtenerTodas();
    }
}
