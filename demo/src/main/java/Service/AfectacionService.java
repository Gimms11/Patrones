package service;

import DTO.AfectacionProductos;
import DAO.DAOTipoAfectacion;
import DAO.DAOFactory;
import java.util.List;

public class AfectacionService {

    private final DAOTipoAfectacion repository;

    public AfectacionService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getAfectacionDAO();
    }

    public List<AfectacionProductos> listarAfectaciones() {
        return repository.obtenerTodas();
    }

    public AfectacionProductos obtenerAfectacion(Long id) {
        if (id == null) return null;
        return repository.obtener(id);
    }
}
