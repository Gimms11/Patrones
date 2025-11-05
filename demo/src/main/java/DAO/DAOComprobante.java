package DAO;
import java.sql.SQLException;
import java.util.List;
import DTO.Comprobante;

public interface DAOComprobante {
    void registarComprobante(Comprobante comp);
    void eliminarComprobante(Comprobante comp);
    List<Comprobante> listarComprobante();
    Comprobante buscarComprobante(Comprobante comp);
    
    // Método flexible de filtrado
    List<Comprobante> filtrarComprobantes(Integer tiempoIndex, String numDocumentoCliente, String numSerie) throws SQLException;
}