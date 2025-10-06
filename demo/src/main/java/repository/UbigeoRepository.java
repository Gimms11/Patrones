package repository;

import DTO.Departamento;
import DTO.Provincia;
import DTO.Distrito;
import java.util.List;

public interface UbigeoRepository {
    List<Departamento> obtenerTodosDepartamentos();
    List<Provincia> obtenerProvinciasPorDepartamento(Long idDepartamento);
    List<Distrito> obtenerDistritosPorProvincia(Long idProvincia);
}
