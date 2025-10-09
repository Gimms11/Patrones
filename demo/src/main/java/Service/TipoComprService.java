package service;

import DTO.TipoComprobante;
import DAO.DAOFactory;
import DAO.DAOTipoComprobante;

import java.util.List;

public class TipoComprService {

    private final DAOTipoComprobante repository;

    public TipoComprService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getTipoComprobanteDAO();
    }

    public List<TipoComprobante> listarTipoComprobantes() {
        return repository.listaTipoComprobante();
    }
}
