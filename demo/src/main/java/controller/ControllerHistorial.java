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
    @FXML private ComboBox<String> listPeriodo;
    @FXML private TextField txtFactura;
    @FXML private TextField txtDocumento;

    // === SERVICIOS ===
    private ComprobanteService comprobanteService;

    @FXML
    public void initialize() {
        try {
            // Inicialización de servicios
            this.comprobanteService = new ComprobanteService();

            // Configurar el listener para txtFactura
            configurarListeners();

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

    private void configurarListeners() {
        // Listener para el campo de número de factura
        txtFactura.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasSerieFilter = newValue != null && !newValue.trim().isEmpty();
            
            // Deshabilitar/habilitar controles según el contenido de txtFactura
            listPeriodo.setDisable(hasSerieFilter);
            txtDocumento.setDisable(hasSerieFilter);
            
            // Limpiar controles si txtFactura tiene contenido
            if (hasSerieFilter) {
                listPeriodo.getSelectionModel().clearSelection();
                txtDocumento.clear();
            } else {
                // Cuando se limpia txtFactura, habilitar los otros controles
                listPeriodo.setDisable(false);
                txtDocumento.setDisable(false);
            }
        });
    }

    private void configurarTabla() {
        //Añadir Ninguno, hoy, hace una semana, hace un mes, hace un año a listPeriodo
        listPeriodo.getItems().addAll("hoy", "una semana", "un mes", "un año", "ninguno");

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

    // === MÉTODO DE FILTRO ===
    @FXML
    private void filtrarComprobantes() {
        // 1. Obtener valores de los filtros
        Integer periodoSelect = null;
        if (listPeriodo != null && !listPeriodo.getSelectionModel().isEmpty()) {
            int selectedIndex = listPeriodo.getSelectionModel().getSelectedIndex();
            // Si se selecciona "ninguno" (índice 4), dejamos periodoSelect como null
            if (selectedIndex != 4) {
                periodoSelect = selectedIndex;
            }
        }

        // 2. Obtener NumFactura y numDocumento
        String numFactura = txtFactura.getText().trim();
        String numDocumento = txtDocumento.getText().trim();

        try {
            // Solo enviar valores si no están vacíos
            String docFiltro = numDocumento.isEmpty() ? null : numDocumento;
            String serieFiltro = numFactura.isEmpty() ? null : numFactura;
            
            List<Comprobante> comprobantes = comprobanteService.listarComprobanteFiltro(
                periodoSelect, // Ya es Integer, no necesita casting
                docFiltro,    // Filtro por número de documento del cliente
                serieFiltro   // Filtro por número de serie/factura
            );
            cargarDatosTabla(comprobantes);
        } catch (Exception e) {
            mostrarError("Error al cargar datos", 
                        "No se pudieron cargar los comprobantes.", 
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
