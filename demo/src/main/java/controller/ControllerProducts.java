package controller;

import DTO.AfectacionProductos;
import DTO.CategoriaProductos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import service.AfectacionService;
import service.CategoriaService;

/**
 * Controlador para la gestión de productos.
 * Administra los ComboBox de tipo de afectación y categoría de productos
 * para dos formularios distintos dentro de la aplicación.
 */
public class ControllerProducts {

    // === COMBOBOX DE AFECTACIÓN ===
    @FXML private ComboBox<AfectacionProductos> listAfectacion;
    @FXML private ComboBox<AfectacionProductos> listAfectacion1;

    private AfectacionService afectacionService;

    // === COMBOBOX DE CATEGORÍA ===
    @FXML private ComboBox<CategoriaProductos> listCategoria;
    @FXML private ComboBox<CategoriaProductos> listCategoria1;

    private CategoriaService categoriaService;

    // =============================================================
    // MÉTODO PRINCIPAL DE INICIALIZACIÓN
    // =============================================================
    @FXML
    public void initialize() {
        try {
            // Inicialización de servicios
            this.afectacionService = new AfectacionService();
            this.categoriaService = new CategoriaService();

            // Configuración de ambos grupos de ComboBox
            configurarComboAfectacion(listAfectacion);
            configurarComboAfectacion(listAfectacion1);

            configurarComboCategoria(listCategoria);
            configurarComboCategoria(listCategoria1);

        } catch (Exception e) {
            mostrarAlerta(AlertType.ERROR, "Error de inicialización", 
                    "Ocurrió un error al cargar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =============================================================
    // CONFIGURACIÓN DE COMBOBOX
    // =============================================================

    /**
     * Carga las afectaciones de productos y las asigna al ComboBox.
     * @param combo ComboBox de afectaciones.
     */
    private void configurarComboAfectacion(ComboBox<AfectacionProductos> combo) {
        var afectaciones = afectacionService.listarAfectaciones();

        if (afectaciones == null || afectaciones.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Sin datos", 
                    "No se encontraron tipos de afectación disponibles.");
            return;
        }

        combo.setItems(FXCollections.observableArrayList(afectaciones));
        combo.setPromptText("Seleccione tipo de afectación");
    }

    /**
     * Carga las categorías de productos y las asigna al ComboBox.
     * @param combo ComboBox de categorías.
     */
    private void configurarComboCategoria(ComboBox<CategoriaProductos> combo) {
        var categorias = categoriaService.listarCategorias();

        if (categorias == null || categorias.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Sin datos", 
                    "No se encontraron categorías de productos disponibles.");
            return;
        }

        combo.setItems(FXCollections.observableArrayList(categorias));
        combo.setPromptText("Seleccione categoría");
    }

    // =============================================================
    // MÉTODOS PARA OBTENER VALORES SELECCIONADOS
    // =============================================================

    /** Devuelve el ID de la afectación seleccionada del primer ComboBox. */
    public Long obtenerIdAfectacionSeleccionada() {
        AfectacionProductos af = listAfectacion.getValue();
        return (af != null) ? af.getIdAfectacion() : null;
    }

    /** Devuelve el ID de la afectación seleccionada del segundo ComboBox. */
    public Long obtenerIdAfectacionSeleccionada1() {
        AfectacionProductos af = listAfectacion1.getValue();
        return (af != null) ? af.getIdAfectacion() : null;
    }

    /** Devuelve el ID de la categoría seleccionada del primer ComboBox. */
    public Long obtenerIdCategoriaSeleccionada() {
        CategoriaProductos cat = listCategoria.getValue();
        return (cat != null) ? cat.getIdCategoria() : null;
    }

    /** Devuelve el ID de la categoría seleccionada del segundo ComboBox. */
    public Long obtenerIdCategoriaSeleccionada1() {
        CategoriaProductos cat = listCategoria1.getValue();
        return (cat != null) ? cat.getIdCategoria() : null;
    }

    // =============================================================
    // MÉTODO AUXILIAR: ALERTAS
    // =============================================================

    /**
     * Muestra una alerta de tipo JavaFX.
     * @param tipo Tipo de alerta (INFORMATION, WARNING, ERROR)
     * @param titulo Título de la ventana de alerta
     * @param mensaje Contenido del mensaje
     */
    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
