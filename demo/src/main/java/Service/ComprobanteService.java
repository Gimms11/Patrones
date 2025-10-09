package service;

import DTO.Comprobante;
import DAO.DAOComprobante;
import DAO.DAOFactory;
import java.util.List;

public class ComprobanteService {

    private final DAOComprobante repository;

    public ComprobanteService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getComprobanteDAO();
    }

    public List<Comprobante> listarComprobante() {
        return repository.listarComprobante();
    }

    public void subirComprobante(Comprobante comp) {
        repository.registarComprobante(comp);
    }
}
