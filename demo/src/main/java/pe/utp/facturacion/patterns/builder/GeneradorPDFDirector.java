package pe.utp.facturacion.patterns.builder;

import pe.utp.facturacion.patterns.builder.GeneradorPDFBuilder;
import pe.utp.facturacion.model.*;
import java.util.List;

public class GeneradorPDFDirector {
    private GeneradorPDFBuilder builder;

    public void setBuilder(GeneradorPDFBuilder builder) {
        this.builder = builder;
    }

    /**
     * Genera y guarda el PDF en rutaSalida.
     * rutaSalida ejemplo: "C:/comprobantes/factura_ES-001.pdf"
     */
    public void generarComprobantePDF(Empresa empresa,
            Cliente cliente,
            Comprobante comprobante,
            List<DetalleComprobante> detalles,
            String rutaSalida) throws Exception {
        System.out.println("[PATRÓN BUILDER] Director orquestando construcción paso a paso de PDF");
        System.out.println("[GRASP: Controller] GeneradorPDFDirector coordina el proceso de construcción del PDF");
        System.out.println("[GRASP: High Cohesion] Director se enfoca únicamente en orquestar la construcción");

        if (builder == null) {
            throw new IllegalStateException("Builder no establecido");
        }
        builder.iniciarDocumento(rutaSalida);
        try {
            System.out.println("[BUILDER] Ejecutando pasos de construcción en secuencia...");
            builder.agregarEncabezado(empresa);
            builder.agregarDatosCliente(cliente, comprobante);
            builder.agregarCuerpo(comprobante, detalles);
            builder.agregarPie(comprobante);
        } finally {
            builder.finalizarDocumento();
            System.out.println("[BUILDER] Construcción de PDF finalizada");
        }
    }
}
