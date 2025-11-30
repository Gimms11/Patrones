package pe.utp.facturacion.persistence.dao;

import pe.utp.facturacion.model.MedioPago;
import java.util.List;

public interface DAOMedioPago {
    List<MedioPago> obtenerTodos();
}