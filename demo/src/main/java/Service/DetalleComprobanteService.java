package service;

import java.util.List;
import DAO.DAODetalleComprobante;
import DAOImpl.DAODetalleComprobanteImpl;
import DTO.DetalleComprobante;

public class DetalleComprobanteService {
    private DAODetalleComprobante dao;

    public DetalleComprobanteService() {
        this.dao = new DAODetalleComprobanteImpl();
    }

    public void registrarDetalleComprobante(DetalleComprobante detalle) {
        dao.registrarDetalleComprobante(detalle);
    }

    public void eliminarDetalleComprobante(DetalleComprobante detalle) {
        dao.eliminarDetalleComprobante(detalle);
    }

    public List<DetalleComprobante> listarDetalleComprobante() {
        return dao.listarDetalleComprobante();
    }

    public List<DetalleComprobante> listarDetallesPorComprobante(Long idComprobante) {
        return dao.listarDetallesPorComprobante(idComprobante);
    }
}