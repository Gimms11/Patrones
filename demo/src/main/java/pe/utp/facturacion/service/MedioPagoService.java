package pe.utp.facturacion.service;

import pe.utp.facturacion.model.MedioPago;
import pe.utp.facturacion.persistence.dao.DAOMedioPago;
import pe.utp.facturacion.persistence.dao.DAOFactory;
import java.util.List;

public class MedioPagoService {

    private final DAOMedioPago repository;

    public MedioPagoService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getMedioPagoDAO();
    }

    public List<MedioPago> listarMedioPagos() {
        return repository.obtenerTodos();
    }
}
