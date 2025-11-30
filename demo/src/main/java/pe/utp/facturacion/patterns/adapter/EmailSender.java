package pe.utp.facturacion.patterns.adapter;

import java.io.File;

/**
 * Adapter Pattern - Define la interfaz para envío de correos
 */
public interface EmailSender {
    /**
     * Envía un correo con archivo adjunto
     * @param to Dirección de correo destino
     * @param subject Asunto del correo
     * @param body Cuerpo del correo
     * @param xmlFile Archivo XML a adjuntar
     * @throws Exception si hay error al enviar
     */
    void sendEmail(String to, String subject, String body, File xmlFile) throws Exception;
}
