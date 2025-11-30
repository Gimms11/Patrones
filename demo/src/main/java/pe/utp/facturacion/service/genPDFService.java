package pe.utp.facturacion.service;

import java.util.List;

import pe.utp.facturacion.persistence.impl.DAODetalleComprobanteImpl;
import pe.utp.facturacion.model.Cliente;
import pe.utp.facturacion.model.Comprobante;
import pe.utp.facturacion.model.DetalleComprobante;
import pe.utp.facturacion.model.Empresa;
import pe.utp.facturacion.patterns.builder.GeneradorPDFBuilder;
import pe.utp.facturacion.patterns.builder.GeneradorBoletaPDF;
import pe.utp.facturacion.patterns.builder.GeneradorFacturaPDF;
import pe.utp.facturacion.patterns.builder.GeneradorPDFDirector;
import java.awt.Desktop;
import java.io.File;

public class genPDFService {
    
    private ManejadorService lectorServicio = new ManejadorService();
    private Empresa emp = lectorServicio.leerEmpresa();

    public void generarPDF(Comprobante comp) {
        try {
            ClienteService clienteService = new ClienteService();
            DAODetalleComprobanteImpl dao = new DAODetalleComprobanteImpl();
            List<DetalleComprobante> detalles = dao.listarDetallesPorComprobante(comp.getIdComprobante());
            Cliente cli = clienteService.obtenerPorId(comp.getIdCliente());

            GeneradorPDFDirector director = new GeneradorPDFDirector();

            String rutaArchivo = "";

            if(comp.getIdTipoComprobante() == 1){
                // FACTURA
                GeneradorPDFBuilder facturaBuilder = new GeneradorFacturaPDF();
                director.setBuilder(facturaBuilder);
                String rutaFactura = "demo\\src\\main\\resources\\com\\example\\PDFs\\factura_F001-000123.pdf";
                rutaArchivo = rutaFactura;
                director.generarComprobantePDF(emp, cli, comp, detalles, rutaFactura);
            } else {
                // BOLETA
                GeneradorPDFBuilder boletaBuilder = new GeneradorBoletaPDF();
                director.setBuilder(boletaBuilder);
                String rutaBoleta = "demo\\src\\main\\resources\\com\\example\\PDFs\\boleta_B001-000123.pdf";
                rutaArchivo = rutaBoleta;
                director.generarComprobantePDF(emp, cli, comp, detalles, rutaBoleta);
            }

            System.out.println("PDFs generados correctamente.");

                // ✅ Abre el PDF en el navegador por defecto
            File archivo = new File(rutaArchivo);
            if (archivo.exists()) {
                Desktop.getDesktop().browse(archivo.toURI());
                // o: Desktop.getDesktop().open(archivo); // abre con lector PDF predeterminado
            } else {
                System.out.println("No se encontró el archivo PDF para abrir.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
