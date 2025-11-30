package pe.utp.facturacion.patterns.strategy.json;

import pe.utp.facturacion.model.Usuario;
import pe.utp.facturacion.patterns.strategy.json.ManejadorJsonI;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManejadorSesion implements ManejadorJsonI<Usuario> {
    private String RUTA_DEFECTO = "demo\\src\\main\\java\\sesionActual.json";

    @Override
    public Usuario leerArchivoJson(String rutaArchivo) {
        try {
            // Si no se proporciona una ruta, usar la ruta por defecto
            if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
                rutaArchivo = this.RUTA_DEFECTO;
            }

            // Crear un StringBuilder para almacenar el contenido del archivo
            StringBuilder jsonContent = new StringBuilder();
            String linea;

            // Leer el archivo línea por línea
            try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
                while ((linea = reader.readLine()) != null) {
                    jsonContent.append(linea.trim());
                }
            }

            // Convertir el contenido JSON en un objeto Empresa manualmente
            String json = jsonContent.toString();
            
            // Eliminar las llaves del inicio y final
            json = json.substring(1, json.length() - 1);
            
            // Crear una nueva empresa
            Usuario usuario = new Usuario();
            
            // Separar los campos por comas y procesar cada uno
            for (String campo : json.split(",")) {
                String[] partes = campo.split(":");
                if (partes.length == 2) {
                    String clave = partes[0].trim().replace("\"", "");
                    String valor = partes[1].trim().replace("\"", "");
                    
                    switch (clave) {
                        case "idUsuario":
                            usuario.setIdUsuario(Long.parseLong(valor));
                            break;
                        case "username":
                            usuario.setUsername(valor);
                            break;
                        case "password":
                            usuario.setPassword(valor);
                            break;
                        case "rol":
                            usuario.setRol(valor);
                            break;
                    }
                }
            }
            
            // Validar campos obligatorios
            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de la empresa es obligatorio");
            }
            
            return usuario;

        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("Error al procesar los datos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

        @Override
    public void escribirArchivoJson(Usuario usuario, String rutaArchivo) {
        String ruta = (rutaArchivo == null || rutaArchivo.trim().isEmpty()) 
                      ? RUTA_DEFECTO : rutaArchivo;

        StringBuilder json = new StringBuilder();
        json.append("{\n")
            .append("  \"idUsuario\": ").append(usuario.getIdUsuario()).append(",\n")
            .append("  \"username\": \"").append(escapeJson(usuario.getUsername())).append("\",\n")
            .append("  \"password\": \"").append(escapeJson(usuario.getPassword())).append("\",\n")
            .append("  \"rol\": \"").append(escapeJson(usuario.getRol())).append("\"\n")
            .append("}");

        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(json.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo JSON de usuario", e);
        }
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\"", "\\\"");
    }
}
