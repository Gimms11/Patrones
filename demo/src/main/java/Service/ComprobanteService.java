package service;

import DTO.Comprobante;
import DAO.DAOComprobante;
import DAO.DAOFactory;
import java.util.List;
import java.util.ArrayList;

public class ComprobanteService {

    private final DAOComprobante repository;

    public ComprobanteService() {
        DAOFactory factory = DAOFactory.getDAOFactory();
        this.repository = factory.getComprobanteDAO();
    }

    public List<Comprobante> listarComprobante() {
        try {
            List<Comprobante> comprobantes = repository.listarComprobante();
            return comprobantes != null ? comprobantes : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar comprobantes: " + e.getMessage(), e);
        }
    }

    public List<Comprobante> listarComprobanteHoy() {
        try {
            List<Comprobante> comprobantes = repository.filtrarComprovanteHoy();
            return comprobantes != null ? comprobantes : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar comprobantes del día: " + e.getMessage(), e);
        }
    }

    public List<Comprobante> listarComprobanteSemana() {
        try {
            List<Comprobante> comprobantes = repository.filtrarComprobantesSemana();
            return comprobantes != null ? comprobantes : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar comprobantes de la semana: " + e.getMessage(), e);
        }
    }

    public List<Comprobante> listarComprobanteMes() {
        try {
            List<Comprobante> comprobantes = repository.filtrarComprobantePorMes();
            return comprobantes != null ? comprobantes : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar comprobantes del mes: " + e.getMessage(), e);
        }
    }

    public List<Comprobante> listarComprobanteAño() {
        try {
            List<Comprobante> comprobantes = repository.filtrarComprobantePorAño();
            return comprobantes != null ? comprobantes : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar comprobantes del año: " + e.getMessage(), e);
        }
    }

    public List<Comprobante> listarComprobanteCliente(Long numDocumento) {
        if (numDocumento == null) {
            throw new IllegalArgumentException("El número de documento no puede ser nulo");
        }
        try {
            List<Comprobante> comprobantes = repository.filtrarComprobantePorCliente(numDocumento);
            return comprobantes != null ? comprobantes : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar comprobantes del cliente: " + e.getMessage(), e);
        }
    }

    public void subirComprobante(Comprobante comp) {
        if (comp == null) {
            throw new IllegalArgumentException("El comprobante no puede ser nulo");
        }
        try {
            repository.registarComprobante(comp);
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar comprobante: " + e.getMessage(), e);
        }
    }
}
