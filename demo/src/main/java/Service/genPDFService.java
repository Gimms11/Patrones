package service;

import java.util.List;

import DTO.Comprobante;
import DTO.DetalleComprobante;

public class genPDFService {
    public static void generarPDF(Comprobante comprobante, List<DetalleComprobante> detalles) {
        // Lógica para generar un archivo PDF con el contenido proporcionado
        System.out.println("Generando PDF para el comprobante: " + comprobante.getSerie());
        // Aquí se implementaría la lógica real de generación de PDF
    }
}
