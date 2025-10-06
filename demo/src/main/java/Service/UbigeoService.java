package service;

import DTO.Departamento;
import DTO.Provincia;
import DTO.Distrito;
import repository.UbigeoRepository;
import java.util.List;

public class UbigeoService {
    private final UbigeoRepository repository;

    public UbigeoService(UbigeoRepository repository) {
        this.repository = repository;
    }

    public List<Departamento> cargarDepartamentos() {
        return repository.obtenerTodosDepartamentos();
    }

    public List<Provincia> cargarProvincias(Long idDepartamento) {
        if (idDepartamento == null) return List.of();
        return repository.obtenerProvinciasPorDepartamento(idDepartamento);
    }

    public List<Distrito> cargarDistritos(Long idProvincia) {
        if (idProvincia == null) return List.of();
        return repository.obtenerDistritosPorProvincia(idProvincia);
    }
}
