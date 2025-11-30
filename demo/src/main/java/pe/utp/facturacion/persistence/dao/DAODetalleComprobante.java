package pe.utp.facturacion.persistence.dao;

import java.util.List;
import pe.utp.facturacion.model.DetalleComprobante;

public interface DAODetalleComprobante {
    void registrarDetalleComprobante(DetalleComprobante detalle);
    void eliminarDetalleComprobante(DetalleComprobante detalle);
    List<DetalleComprobante> listarDetalleComprobante();
    List<DetalleComprobante> listarDetallesPorComprobante(Long idComprobante);
}
