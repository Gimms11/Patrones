package DAO;
import java.util.List;
import DTO.Comprobante;

public interface DAOComprobante {
    public void registarComprobante(Comprobante comp);
    public void eliminarComprobante(Comprobante comp);
    public List<Comprobante> listarComprobante();
    public Comprobante buscarComprobante(Object key);
}