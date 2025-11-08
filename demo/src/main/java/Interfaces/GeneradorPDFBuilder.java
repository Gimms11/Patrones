package Interfaces;


import DTO.*;
import java.util.List;

public interface GeneradorPDFBuilder {
    void iniciarDocumento(String rutaSalida) throws Exception; // abre Document y PdfWriter
    void agregarEncabezado(Empresa empresa) throws Exception;
    void agregarDatosCliente(Cliente cliente, Comprobante comprobante) throws Exception;
    void agregarCuerpo(Comprobante comprobante, java.util.List<DetalleComprobante> detalles) throws Exception;
    void agregarPie(Comprobante comprobante) throws Exception;
    void finalizarDocumento() throws Exception; // cierra Document
}
