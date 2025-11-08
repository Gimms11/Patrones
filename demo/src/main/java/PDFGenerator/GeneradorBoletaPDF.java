package PDFGenerator;

import Abstract.AbstractGeneradorPDF;
import DTO.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


import java.util.List;
import java.math.BigDecimal;

public class GeneradorBoletaPDF extends AbstractGeneradorPDF {

    @Override
    public void agregarEncabezado(Empresa empresa) throws Exception {
        PdfPTable tabla = crearTabla(new float[]{3f, 1f});
        PdfPCell left = new PdfPCell();
        left.setBorder(PdfPCell.NO_BORDER);
        left.addElement(new Paragraph(empresa.getNombre(), fontTitle));
        left.addElement(new Paragraph("Dirección: " + empresa.getDireccion(), fontNormal));
        tabla.addCell(left);

        PdfPCell right = new PdfPCell();
        right.setBorder(PdfPCell.NO_BORDER);
        Paragraph titulo = new Paragraph("BOLETA", new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD));
        titulo.setAlignment(Element.ALIGN_RIGHT);
        right.addElement(titulo);
        tabla.addCell(right);

        document.add(tabla);
        document.add(Chunk.NEWLINE);
    }

    @Override
    public void agregarDatosCliente(Cliente cliente, Comprobante comprobante) throws Exception {
        PdfPTable tbl = crearTabla(new float[]{1f, 1f});
        PdfPCell left = new PdfPCell();
        left.setBorder(PdfPCell.NO_BORDER);
        left.addElement(new Paragraph("Cliente:", fontBold));
        left.addElement(new Paragraph(cliente.getNombres() + " " + (cliente.getApellidos()!=null?cliente.getApellidos():""), fontNormal));
        left.addElement(new Paragraph("DNI: " + cliente.getNumDocumento(), fontNormal));
        tbl.addCell(left);

        PdfPCell right = new PdfPCell();
        right.setBorder(PdfPCell.NO_BORDER);
        right.addElement(new Paragraph("N° boleta: " + comprobante.getSerie(), fontBold));
        right.addElement(new Paragraph("Fecha: " + comprobante.getFechaEmision().format(dateFmt), fontNormal));
        tbl.addCell(right);

        document.add(tbl);
        document.add(Chunk.NEWLINE);
    }

    @Override
    public void agregarCuerpo(Comprobante comprobante, List<DetalleComprobante> detalles) throws Exception {
        PdfPTable table = crearTabla(new float[]{1f, 6f, 2f});
        table.addCell(celdaConBorde("CANT.", fontBold));
        table.addCell(celdaConBorde("DESCRIPCIÓN", fontBold));
        table.addCell(celdaConBorde("IMPORTE", fontBold));

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleComprobante d : detalles) {
            table.addCell(celdaConBorde(String.valueOf(d.getCantidadProductos()), fontNormal));
            table.addCell(celdaConBorde(d.getNombreProducto() != null ? d.getNombreProducto() : "-", fontNormal));
            table.addCell(celdaConBorde(currency.format(d.getTotal()), fontNormal));
            total = total.add(d.getTotal() != null ? d.getTotal() : BigDecimal.ZERO);
        }

        document.add(table);

        PdfPTable resumen = crearTabla(new float[]{7f, 2f});
        PdfPCell vacio = new PdfPCell(new Phrase(""));
        vacio.setBorder(PdfPCell.NO_BORDER);
        resumen.addCell(vacio);
        PdfPCell tot = celdaConBorde("TOTAL: " + currency.format(total), fontBold);
        tot.setHorizontalAlignment(Element.ALIGN_RIGHT);
        resumen.addCell(tot);

        document.add(resumen);
    }

    @Override
    public void agregarPie(Comprobante comprobante) throws Exception {
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Gracias por su compra.", fontNormal));
    }
}

