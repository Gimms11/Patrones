package pe.utp.facturacion.service;

import pe.utp.facturacion.model.TipoComprobante;
import pe.utp.facturacion.persistence.dao.DAOFactory;
import pe.utp.facturacion.persistence.dao.DAOTipoComprobante;

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
