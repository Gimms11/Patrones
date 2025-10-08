package service;

import DTO.MedioPago;
import DAO.DAOMedioPago;
import DAO.DAOFactory;
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
