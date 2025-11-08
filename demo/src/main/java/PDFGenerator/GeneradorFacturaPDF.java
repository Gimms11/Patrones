package PDFGenerator;

import Abstract.AbstractGeneradorPDF;
import DTO.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.math.BigDecimal;
import java.util.List;

public class GeneradorFacturaPDF extends AbstractGeneradorPDF {

    @Override
    public void agregarEncabezado(Empresa empresa) throws Exception {
        // Tabla 2 columnas: datos empresa | título factura y serie
        PdfPTable tabla = crearTabla(new float[]{3f, 1f});
        // izquierda: nombre empresa y direccion
        PdfPCell izquierda = new PdfPCell();
        izquierda.setBorder(PdfPCell.NO_BORDER);
        izquierda.addElement(new Paragraph(empresa.getNombre(), fontTitle));
        izquierda.addElement(new Paragraph("RUC: " + empresa.getRuc(), fontNormal));
        if (empresa.getDireccion() != null) izquierda.addElement(new Paragraph(empresa.getDireccion(), fontNormal));
        if (empresa.getCorreo() != null) izquierda.addElement(new Paragraph(empresa.getCorreo(), fontNormal));
        tabla.addCell(izquierda);

        // derecha: TITULO FACTURA
        PdfPCell derecha = new PdfPCell();
        derecha.setBorder(PdfPCell.NO_BORDER);
        Paragraph titulo = new Paragraph("FACTURA", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        titulo.setAlignment(Element.ALIGN_RIGHT);
        derecha.addElement(titulo);
        tabla.addCell(derecha);

        document.add(tabla);
        document.add(Chunk.NEWLINE);
    }

    @Override
    public void agregarDatosCliente(Cliente cliente, Comprobante comprobante) throws Exception {
        PdfPTable tbl = crearTabla(new float[]{1f, 1f});
        PdfPCell left = new PdfPCell();
        left.setBorder(PdfPCell.NO_BORDER);
        left.addElement(new Paragraph("Facturar a:", fontBold));
        left.addElement(new Paragraph(cliente.getNombres() + " " + (cliente.getApellidos()!=null?cliente.getApellidos():""), fontNormal));
        left.addElement(new Paragraph("Documento: " + cliente.getNumDocumento(), fontNormal));
        left.addElement(new Paragraph("Dirección: " + (cliente.getDireccion()!=null?cliente.getDireccion():""), fontNormal));
        tbl.addCell(left);

        PdfPCell right = new PdfPCell();
        right.setBorder(PdfPCell.NO_BORDER);
        right.addElement(new Paragraph("N° de factura: " + comprobante.getSerie(), fontBold));
        right.addElement(new Paragraph("Fecha: " + comprobante.getFechaEmision().format(dateFmt), fontNormal));
        tbl.addCell(right);

        document.add(tbl);
        document.add(Chunk.NEWLINE);
    }

    @Override
    public void agregarCuerpo(Comprobante comprobante, List<DetalleComprobante> detalles) throws Exception {
        // Tabla: Cant | Descripcion | Precio Unit | Importe
        PdfPTable table = crearTabla(new float[]{1f, 5f, 2f, 2f});
        table.addCell(celdaConBorde("CANT.", fontBold));
        table.addCell(celdaConBorde("DESCRIPCIÓN", fontBold));
        table.addCell(celdaConBorde("PRECIO UNITARIO", fontBold));
        table.addCell(celdaConBorde("IMPORTE", fontBold));

        for (DetalleComprobante d : detalles) {
            table.addCell(celdaConBorde(String.valueOf(d.getCantidadProductos()), fontNormal));
            table.addCell(celdaConBorde(d.getNombreProducto() != null ? d.getNombreProducto() : "-", fontNormal));
            table.addCell(celdaConBorde(currency.format(d.getPrecioUnitario()), fontNormal));
            table.addCell(celdaConBorde(currency.format(d.getTotal()), fontNormal));
        }

        // Subtotal, IGV, Total (suponiendo IGV 18% o calcula según tus datos)
        BigDecimal subtotal = comprobante.getDevengado() != null ? comprobante.getDevengado() : BigDecimal.ZERO;
        BigDecimal total = comprobante.getTotalFinal() != null ? comprobante.getTotalFinal() : BigDecimal.ZERO;
        BigDecimal igv = total.subtract(subtotal);

        // Añadir filas resumen en una tabla separada (para que quede alineado a la derecha)
        PdfPTable resumen = crearTabla(new float[]{6f, 2f});
        resumen.setSpacingBefore(6f);
        resumen.addCell(celda("",fontNormal)); // celda vacía para alineación
        resumen.addCell(celdaConBorde("",fontNormal)); // vacío

        resumen.addCell(celda("", fontNormal));
        PdfPCell sub = celdaConBorde("Subtotal: " + currency.format(subtotal), fontNormal);
        sub.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumen.addCell(sub);

        resumen.addCell(celda("", fontNormal));
        PdfPCell igvCell = celdaConBorde("IGV: " + currency.format(igv), fontNormal);
        igvCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumen.addCell(igvCell);

        resumen.addCell(celda("", fontNormal));
        PdfPCell totalCell = celdaConBorde("TOTAL: " + currency.format(total), fontBold);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumen.addCell(totalCell);

        document.add(table);
        document.add(resumen);
    }

    @Override
    public void agregarPie(Comprobante comprobante) throws Exception {
        document.add(Chunk.NEWLINE);
        Paragraph firma = new Paragraph("Firma autorizada", fontNormal);
        firma.setSpacingBefore(20f);
        document.add(firma);
        // Si quieres puedes agregar imagen de firma con Image.getInstance(path)
    }
}

