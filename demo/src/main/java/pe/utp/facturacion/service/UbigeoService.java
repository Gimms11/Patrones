package pe.utp.facturacion.service;

import pe.utp.facturacion.model.Departamento;
import pe.utp.facturacion.model.Provincia;
import pe.utp.facturacion.model.Distrito;

import java.util.List;

import pe.utp.facturacion.persistence.dao.DAOFactory;
import pe.utp.facturacion.persistence.dao.DAOUbigeo;

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

    public Distrito obtenerDistrito(Long id) {
        if (id == null) return null;
        return repository.obtenerDistrito(id);
    }

    public List<Long> obtenerIds(Long id){
        if (id == null) return null;
        return repository.obtenerIds(id);
    }
}
