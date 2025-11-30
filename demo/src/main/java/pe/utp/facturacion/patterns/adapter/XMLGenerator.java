package pe.utp.facturacion.patterns.adapter;

import pe.utp.facturacion.model.Comprobante;

/**
 * Strategy Pattern - Define la interfaz para generar XML
 */
public interface XMLGenerator {
    /**
     * Genera el contenido XML para un comprobante
     * 
     * @param comprobante El comprobante a convertir a XML
     * @return String con el contenido XML
     */
    String generar(Comprobante comprobante);
}
