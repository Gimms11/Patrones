package pe.utp.facturacion.persistence.dao;

import pe.utp.facturacion.model.Departamento;
import pe.utp.facturacion.model.Provincia;
import pe.utp.facturacion.model.Distrito;
import java.util.List;

public interface DAOUbigeo {
    List<Departamento> obtenerTodosDepartamentos();
    List<Provincia> obtenerProvinciasPorDepartamento(Long idDepartamento);
    List<Distrito> obtenerDistritosPorProvincia(Long idProvincia);
    Distrito obtenerDistrito(Long id);
    List<Long> obtenerIds(Long idDistrito);
}
