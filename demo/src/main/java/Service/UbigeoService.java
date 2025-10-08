package service;

import DTO.Departamento;
import DTO.Provincia;
import DTO.Distrito;

import java.util.List;

import DAO.DAOFactory;
import DAO.DAOUbigeo;

public class UbigeoService {
    private final DAOUbigeo repository;

    public UbigeoService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getUbigeoDAO();
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
