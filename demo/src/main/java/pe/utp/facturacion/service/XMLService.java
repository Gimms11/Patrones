package pe.utp.facturacion.service;

import pe.utp.facturacion.patterns.adapter.EmailSender;
import pe.utp.facturacion.patterns.adapter.MailTrapEmailAdapter;
import pe.utp.facturacion.patterns.adapter.XMLGenerator;
import pe.utp.facturacion.patterns.adapter.XMLGeneratorFactory;
import pe.utp.facturacion.model.Comprobante;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FACADE Pattern - Orquesta todas las operaciones de XML y envío de correo
 * Simplifica la interacción con los demás componentes
 */
public class XMLService {

    // Ruta donde se guardarán los XMLs
    private static final String XML_OUTPUT_DIR = "src/main/resources/pe/utp/facturacion/xml";

    private EmailSender emailSender;

    public XMLService() {
        // Inyectar el adapter de email
        this.emailSender = new MailTrapEmailAdapter();

        // Crear directorio si no existe
        crearDirectorioSiNoExiste();
    }

    /**
     * MÉTODO PRINCIPAL DEL FACADE
     * Genera XML para un comprobante, lo guarda y lo envía por correo
     * 
     * @param comprobante   El comprobante a procesar
     * @param correoDestino Email del destinatario
     * @throws Exception Si hay error en cualquier paso
     */
    public void generarYEnviarXML(Comprobante comprobante, String correoDestino) throws Exception {
        try {
            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("🔄 Iniciando generación de XML...");
            System.out.println("═══════════════════════════════════════════════════════");

            // Paso 1: Validar datos de entrada
            validarEntrada(comprobante, correoDestino);

            // Paso 2: Obtener el generador XML apropiado usando Factory
            System.out.println("📋 Obteniendo generador para tipo: " + comprobante.getIdTipoComprobante());
            XMLGenerator generador = XMLGeneratorFactory.get(comprobante.getIdTipoComprobante());

            // Paso 3: Generar contenido XML
            System.out.println("✏️  Generando contenido XML...");
            String contenidoXML = generador.generar(comprobante);

            // Paso 4: Guardar XML en archivo
            System.out.println("💾 Guardando archivo XML...");
            File archivoXML = guardarXML(comprobante, contenidoXML);
            System.out.println("✓ XML guardado en: " + archivoXML.getAbsolutePath());

            // Paso 5: Enviar correo con el XML adjunto
            System.out.println("📧 Enviando correo a: " + correoDestino);
            enviarCorreoConXML(comprobante, correoDestino, archivoXML);

            System.out.println("═══════════════════════════════════════════════════════");
            System.out.println("✅ Proceso completado exitosamente");
            System.out.println("═══════════════════════════════════════════════════════");

        } catch (Exception e) {
            System.err.println("❌ Error durante la generación de XML: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Valida los datos de entrada
     */
    private void validarEntrada(Comprobante comprobante, String correoDestino) throws Exception {
        if (comprobante == null) {
            throw new IllegalArgumentException("El comprobante no puede ser nulo");
        }
        if (comprobante.getIdTipoComprobante() == null) {
            throw new IllegalArgumentException("El tipo de comprobante no está definido");
        }
        if (correoDestino == null || correoDestino.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo destino no puede estar vacío");
        }
        if (!correoDestino.contains("@")) {
            throw new IllegalArgumentException("El correo destino no es válido");
        }
    }

    /**
     * Guarda el contenido XML en un archivo
     */
    private File guardarXML(Comprobante comprobante, String contenidoXML) throws IOException {
        // Generar nombre de archivo único
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nombreArchivo = String.format(
                "Comprobante_%s_%s.xml",
                comprobante.getSerie() != null ? comprobante.getSerie() : "SN",
                timestamp);

        // Ruta completa del archivo
        String rutaCompleta = XML_OUTPUT_DIR + File.separator + nombreArchivo;
        File archivo = new File(rutaCompleta);

        // Escribir contenido
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write(contenidoXML);
        }

        return archivo;
    }

    /**
     * Envía el correo con el XML adjunto
     */
    private void enviarCorreoConXML(Comprobante comprobante, String correoDestino, File archivoXML) throws Exception {
        String asunto = String.format(
                "Comprobante %s - %s",
                comprobante.getSerie() != null ? comprobante.getSerie() : "SN",
                comprobante.getTotalFinal() != null ? "S/ " + comprobante.getTotalFinal() : "Sin total");

        String cuerpo = generarCuerpoCorreo(comprobante);

        emailSender.sendEmail(correoDestino, asunto, cuerpo, archivoXML);
    }

    /**
     * Genera el cuerpo del correo de forma profesional, diferenciado por tipo de
     * comprobante
     */
    private String generarCuerpoCorreo(Comprobante comprobante) {
        Long idTipoComprobante = comprobante.getIdTipoComprobante();

        // Factura (ID = 1)
        if (idTipoComprobante != null && idTipoComprobante == 1) {
            return generarCuerpoFactura(comprobante);
        }
        // Boleta (ID = 2)
        else if (idTipoComprobante != null && idTipoComprobante == 2) {
            return generarCuerpoBoleta(comprobante);
        }
        // Por defecto (genérico)
        else {
            return generarCuerpoGenerico(comprobante);
        }
    }

    /**
     * Cuerpo de correo para FACTURAS (comprobante formal para RUC)
     */
    private String generarCuerpoFactura(Comprobante comprobante) {
        StringBuilder body = new StringBuilder();
        body.append("════════════════════════════════════════════════════════════\n");
        body.append("                    FACTURA ELECTRÓNICA\n");
        body.append("════════════════════════════════════════════════════════════\n\n");

        body.append("Estimado cliente,\n\n");
        body.append("Adjunto encontrará la FACTURA ELECTRÓNICA de su transacción.\n");
        body.append("Este es un comprobante fiscal válido para operaciones con RUC.\n\n");

        body.append("╔════════════════════════════════════════════════════════════╗\n");
        body.append("║                  INFORMACIÓN DEL COMPROBANTE              ║\n");
        body.append("╚════════════════════════════════════════════════════════════╝\n\n");

        if (comprobante.getSerie() != null) {
            body.append("  Número de Factura: ").append(comprobante.getSerie()).append("\n");
        }
        if (comprobante.getClienteDocumento() != null) {
            body.append("  RUC Cliente:       ").append(comprobante.getClienteDocumento()).append("\n");
        }
        if (comprobante.getNombreCliente() != null) {
            body.append("  Razón Social:      ").append(comprobante.getNombreCliente()).append("\n");
        }
        if (comprobante.getFechaEmision() != null) {
            body.append("  Fecha Emisión:     ").append(comprobante.getFechaEmision()).append("\n");
        }
        if (comprobante.getMedioPago() != null) {
            body.append("  Medio de Pago:     ").append(comprobante.getMedioPago()).append("\n");
        }
        if (comprobante.getDireccionEnvio() != null) {
            body.append("  Dirección:         ").append(comprobante.getDireccionEnvio()).append("\n");
        }

        body.append("\n╔════════════════════════════════════════════════════════════╗\n");
        body.append("║                      MONTOS TOTALES                        ║\n");
        body.append("╚════════════════════════════════════════════════════════════╝\n\n");

        if (comprobante.getDevengado() != null) {
            body.append("  Subtotal:          S/ ").append(comprobante.getDevengado()).append("\n");
        }
        if (comprobante.getTotalFinal() != null && comprobante.getDevengado() != null) {
            body.append("  IGV (18%):         S/ ")
                    .append(comprobante.getTotalFinal().subtract(comprobante.getDevengado())).append("\n");
        }
        if (comprobante.getTotalFinal() != null) {
            body.append("  ═════════════════════════════════════\n");
            body.append("  TOTAL FACTURA:     S/ ").append(comprobante.getTotalFinal()).append("\n");
            body.append("  ═════════════════════════════════════\n");
        }

        body.append("\n📋 IMPORTANTE:\n");
        body.append("   • Esta factura tiene validez legal y fiscal\n");
        body.append("   • Guarde este comprobante para sus registros\n");
        body.append("   • Para consultas, contacte con nuestro equipo\n\n");

        body.append("Gracias por su confianza en MiTrufely S.A.C\n");
        body.append("══════════════════════════════════════════════════════════════\n");
        body.append("Sistema de Comprobantes Electrónicos - MiTrufely S.A.C\n");
        body.append("══════════════════════════════════════════════════════════════\n");

        return body.toString();
    }

    /**
     * Cuerpo de correo para BOLETAS (comprobante simplificado)
     */
    private String generarCuerpoBoleta(Comprobante comprobante) {
        StringBuilder body = new StringBuilder();
        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        body.append("                      BOLETA DE VENTA\n");
        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");

        body.append("Estimado cliente,\n\n");
        body.append("Adjunto encontrará su BOLETA DE VENTA.\n");
        body.append("Comprobante simplificado de su transacción.\n\n");

        body.append("┌──────────────────────────────────────────────────────────┐\n");
        body.append("│              DETALLES DE LA TRANSACCIÓN                  │\n");
        body.append("└──────────────────────────────────────────────────────────┘\n\n");

        if (comprobante.getSerie() != null) {
            body.append("  Número de Boleta:  ").append(comprobante.getSerie()).append("\n");
        }
        if (comprobante.getClienteDocumento() != null) {
            body.append("  Documento Cliente: ").append(comprobante.getClienteDocumento()).append("\n");
        }
        if (comprobante.getNombreCliente() != null) {
            body.append("  Nombre:            ").append(comprobante.getNombreCliente()).append("\n");
        }
        if (comprobante.getFechaEmision() != null) {
            body.append("  Fecha:             ").append(comprobante.getFechaEmision()).append("\n");
        }
        if (comprobante.getMedioPago() != null) {
            body.append("  Medio de Pago:     ").append(comprobante.getMedioPago()).append("\n");
        }

        body.append("\n┌──────────────────────────────────────────────────────────┐\n");
        body.append("│                    MONTO TOTAL                          │\n");
        body.append("└──────────────────────────────────────────────────────────┘\n\n");

        if (comprobante.getTotalFinal() != null) {
            body.append("  💰 TOTAL:          S/ ").append(comprobante.getTotalFinal()).append("\n");
        }

        body.append("\n✓ Gracias por su compra.\n");
        body.append("✓ Guarde esta boleta como comprobante de su transacción.\n\n");

        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        body.append("Sistema de Comprobantes - MiTrufely S.A.C\n");
        body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");

        return body.toString();
    }

    /**
     * Cuerpo de correo genérico (fallback)
     */
    private String generarCuerpoGenerico(Comprobante comprobante) {
        StringBuilder body = new StringBuilder();
        body.append("Estimado cliente,\n\n");
        body.append("Adjunto encontrará el comprobante de su transacción.\n\n");

        if (comprobante.getSerie() != null) {
            body.append("Serie: ").append(comprobante.getSerie()).append("\n");
        }
        if (comprobante.getFechaEmision() != null) {
            body.append("Fecha: ").append(comprobante.getFechaEmision()).append("\n");
        }
        if (comprobante.getNombreCliente() != null) {
            body.append("Cliente: ").append(comprobante.getNombreCliente()).append("\n");
        }
        if (comprobante.getTotalFinal() != null) {
            body.append("Total: S/ ").append(comprobante.getTotalFinal()).append("\n");
        }

        body.append("\nGracias por su compra.\n");
        body.append("---\n");
        body.append("Sistema de Comprobantes - MiTrufely S.A.C\n");

        return body.toString();
    }

    /**
     * Crea el directorio de salida si no existe
     */
    private void crearDirectorioSiNoExiste() {
        try {
            Files.createDirectories(Paths.get(XML_OUTPUT_DIR));
        } catch (IOException e) {
            System.err.println("Advertencia: No se pudo crear el directorio " + XML_OUTPUT_DIR);
        }
    }
}
