package pe.utp.facturacion.service;

import java.util.List;
import pe.utp.facturacion.persistence.dao.DAODetalleComprobante;
import pe.utp.facturacion.persistence.impl.DAODetalleComprobanteImpl;
import pe.utp.facturacion.model.DetalleComprobante;

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