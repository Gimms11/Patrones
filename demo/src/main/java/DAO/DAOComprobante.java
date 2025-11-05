package DAO;
import java.time.LocalDate;
import java.util.List;
import DTO.Comprobante;

public interface DAOComprobante {
    public void registarComprobante(Comprobante comp);
    public void eliminarComprobante(Comprobante comp);
    public List<Comprobante> listarComprobante();
    public Comprobante buscarComprobante(Comprobante comp);
    public List<Comprobante> filtrarComprovanteHoy ();
    public List<Comprobante> filtrarComprobantesSemana();
    public List<Comprobante> filtrarComprobantePorMes();
    public List<Comprobante> filtrarComprobantePorAño();
    public List<Comprobante> filtrarComprobantePorCliente(Long clienteId);
}