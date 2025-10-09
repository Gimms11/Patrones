package DAO;

import java.util.List;
import DTO.DetalleComprobante;

public interface DAODetalleComprobante {
    void registrarDetalleComprobante(DetalleComprobante detalle);
    void eliminarDetalleComprobante(DetalleComprobante detalle);
    List<DetalleComprobante> listarDetalleComprobante();
    List<DetalleComprobante> listarDetallesPorComprobante(Long idComprobante);
}
