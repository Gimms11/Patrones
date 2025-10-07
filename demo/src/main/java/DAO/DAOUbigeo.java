package DAO;

import DTO.Departamento;
import DTO.Provincia;
import DTO.Distrito;
import java.util.List;

public interface DAOUbigeo {
    List<Departamento> obtenerTodosDepartamentos();
    List<Provincia> obtenerProvinciasPorDepartamento(Long idDepartamento);
    List<Distrito> obtenerDistritosPorProvincia(Long idProvincia);
}
