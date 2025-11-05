package controller;

import java.util.List;
import java.util.ArrayList;

import DTO.Comprobante;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.SimpleStringProperty;
import service.ComprobanteService;

public class ControllerHistorial {
    
    // --- TableView y sus columnas ---
    @FXML private TableView<Comprobante> tablaComprobantes;
    @FXML private TableColumn<Comprobante, String> col_serie;
    @FXML private TableColumn<Comprobante, String> col_docCliente;
    @FXML private TableColumn<Comprobante, String> col_nomClientes;
    @FXML private TableColumn<Comprobante, String> col_distritoCliente;
    @FXML private TableColumn<Comprobante, String> col_direccionEnvio;
    @FXML private TableColumn<Comprobante, String> col_fechaEmision;
    @FXML private TableColumn<Comprobante, String> col_medioPago;
    @FXML private TableColumn<Comprobante, String> col_totalFinal;

    // === CAMPOS DE FILTRO ===
    @FXML private TextField txtNumeroDoc;

    // === SERVICIOS ===
    private ComprobanteService comprobanteService;

    @FXML
    public void initialize() {
        try {
            // Inicialización de servicios
            this.comprobanteService = new ComprobanteService();

            // Configuración de la tabla
            configurarTabla();

            // Cargar datos iniciales (últimos 7 días por defecto)
            cargarDatosTabla();

        } catch (Exception e) {
            mostrarError("Error de inicialización", 
                "No se pudo inicializar la ventana de historial.", 
                e.getMessage());
        }
    }

    private void configurarTabla() {
        // Configurar columna Serie
        col_serie.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getSerie() != null ? 
                cellData.getValue().getSerie() : ""
            );
        });

        // Configurar columna documento Cliente
        col_docCliente.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getClienteDocumento() != null ? 
                cellData.getValue().getClienteDocumento () : ""
            );
        });

        // Configurar columna Medio de Pago
        col_medioPago.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getMedioPago() != null ? 
                cellData.getValue().getMedioPago() : ""
            );
        });

        // Configurar columna Nombre Cliente
        col_nomClientes.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getNombreCliente() != null ? 
                cellData.getValue().getNombreCliente() : ""
            );
        });

        // Configurar columna Distrito Cliente
        col_distritoCliente.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getClienteDistrito() != null ? 
                cellData.getValue().getClienteDistrito() : ""
            );
        });

        // Configurar columna Dirección de Envío
        col_direccionEnvio.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getDireccionEnvio() != null ? 
                cellData.getValue().getDireccionEnvio() : ""
            );
        });

        // Configurar columna Fecha de Emisión
        col_fechaEmision.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getFechaEmision() != null ? 
                cellData.getValue().getFechaEmision().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : ""
            );
        });

        // Configurar columna Total Final
        col_totalFinal.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getTotalFinal() != null ? 
                String.format("S/ %.2f", cellData.getValue().getTotalFinal()) : ""
            );
        });
    }

    // === MÉTODOS DE CARGA DE DATOS ===

    private void cargarDatosTabla(List<Comprobante> comprobantes) {
        try {
            if (comprobantes == null) {
                comprobantes = new ArrayList<>();
            }
            tablaComprobantes.setItems(FXCollections.observableArrayList(comprobantes));
            tablaComprobantes.refresh();
        } catch (Exception e) {
            mostrarError("Error al cargar datos", 
                        "No se pudieron cargar los comprobantes.", 
                        e.getMessage());
        }
    }

    public void cargarDatosTabla() {
        try {
            List<Comprobante> comprobantes = comprobanteService.listarComprobante();
            cargarDatosTabla(comprobantes);
        } catch (Exception e) {
            mostrarError("Error al cargar datos", 
                        "No se pudieron cargar los comprobantes.", 
                        e.getMessage());
        }
    }

    @FXML
    public void cargarComprobantesHoy() {
        try {
            List<Comprobante> comprobantes = comprobanteService.listarComprobanteHoy();
            cargarDatosTabla(comprobantes);
        } catch (Exception e) {
            mostrarError("Error al cargar", "No se pudieron cargar los comprobantes del día.", e.getMessage());
        }
    }

    @FXML
    public void cargarComprobantesSemanales() {
        try {
            List<Comprobante> comprobantes = comprobanteService.listarComprobanteSemana();
            cargarDatosTabla(comprobantes);
        } catch (Exception e) {
            mostrarError("Error al cargar", "No se pudieron cargar los comprobantes de la semana.", e.getMessage());
        }
    }

    @FXML
    public void cargarComprobantesMensuales() {
        try {
            List<Comprobante> comprobantes = comprobanteService.listarComprobanteMes();
            cargarDatosTabla(comprobantes);
        } catch (Exception e) {
            mostrarError("Error al cargar", "No se pudieron cargar los comprobantes del mes.", e.getMessage());
        }
    }

    @FXML
    public void cargarComprobantesAnuales() {
        try {
            List<Comprobante> comprobantes = comprobanteService.listarComprobanteAño();
            cargarDatosTabla(comprobantes);
        } catch (Exception e) {
            mostrarError("Error al cargar", "No se pudieron cargar los comprobantes del año.", e.getMessage());
        }
    }

    @FXML
    public void filtrarPorCliente() {
        try {
            String numDoc = txtNumeroDoc.getText().trim();
            if (numDoc.isEmpty()) {
                cargarComprobantesSemanales(); // Si no hay número, mostrar vista por defecto
                return;
            }

            List<Comprobante> comprobantes = comprobanteService.listarComprobanteCliente(Long.parseLong(numDoc));
            if (comprobantes.isEmpty()) {
                mostrarInfo("Sin resultados", 
                    "No se encontraron comprobantes para el cliente con documento: " + numDoc);
            }
            cargarDatosTabla(comprobantes);
        } catch (NumberFormatException e) {
            mostrarAdvertencia("Formato inválido", 
                "El número de documento debe contener solo números.");
        } catch (Exception e) {
            mostrarError("Error al filtrar", 
                "No se pudieron cargar los comprobantes del cliente.", 
                e.getMessage());
        }
    }

    // === MÉTODOS DE ALERTA ===

    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String titulo, String mensaje, String detalles) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje + "\n\nDetalles: " + detalles);
        alert.showAndWait();
    }
}
