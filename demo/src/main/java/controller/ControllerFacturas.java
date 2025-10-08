package controller;

import DTO.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import service.*;

import java.util.List;

public class ControllerFacturas {

    // === ELEMENTOS DE LA VISTA ===
    @FXML private ComboBox<Producto> listProductos;
    @FXML private ComboBox<TipoDocumento> listTipoDoc;

    @FXML private TextField prodPrecio;
    @FXML private TextField prodStock;
    @FXML private TextField prodCat;
    @FXML private TextField prodAfec;
    @FXML private TextField prodUnidad;

    @FXML private TextField numDocumento;
    @FXML private TextField nomApeCliente;

    // === SERVICIOS Y LISTAS ===
    private ProductoService productoService;
    private AfectacionService afectacionService;
    private CategoriaService categoriaService;
    private ClienteService clienteService;
    private TipoDocumentoService tipoDocumentoService;

    private List<AfectacionProductos> listAfectacionProductos;
    private List<CategoriaProductos> listCategoriaProductos;
    private List<Cliente> listClientes;

    // =============================================================
    // INITIALIZE
    // =============================================================
    @FXML
    public void initialize() {
        try {
            // Inyección manual de servicios
            this.productoService = new ProductoService();
            this.afectacionService = new AfectacionService();
            this.categoriaService = new CategoriaService();
            this.tipoDocumentoService = new TipoDocumentoService();
            this.clienteService = new ClienteService();

            // Cargar datos base
            this.listAfectacionProductos = afectacionService.listarAfectaciones();
            this.listCategoriaProductos = categoriaService.listarCategorias();
            this.listClientes = clienteService.obtenerTodos();

            // Configurar combos
            configurarComboProd(listProductos);
            configurarComboDoc(listTipoDoc);

            // Listener para el producto seleccionado
            listProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) mostrarDatosProducto(newVal);
            });

        } catch (Exception e) {
            mostrarAlerta(AlertType.ERROR, "Error de inicialización", "Ocurrió un error al cargar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =============================================================
    // MÉTODOS DE CONFIGURACIÓN
    // =============================================================

    /** Configura el ComboBox de productos con los datos del servicio */
    private void configurarComboProd(ComboBox<Producto> combo) {
        var productos = productoService.obtenerTodos();
        combo.setItems(FXCollections.observableArrayList(productos));
        combo.setPromptText("Seleccione un producto");
    }

    /** Configura el ComboBox de tipo de documento con los datos del servicio */
    private void configurarComboDoc(ComboBox<TipoDocumento> combo) {
        var tipos = tipoDocumentoService.cargarTipoDocumentos();
        combo.setItems(FXCollections.observableArrayList(tipos));
        combo.setPromptText("Seleccione tipo de documento");
    }

    // =============================================================
    // EVENTOS DE INTERFAZ
    // =============================================================

    /** Muestra la información del producto seleccionado en los campos */
    private void mostrarDatosProducto(Producto producto) {
        prodPrecio.setText(String.valueOf(producto.getPrecio()));
        prodStock.setText(String.valueOf(producto.getStock()));
        prodUnidad.setText(producto.getUnidadMedida());

        // Buscar nombres de afectación y categoría
        String nombreAfectacion = listAfectacionProductos.stream()
                .filter(a -> a.getIdAfectacion().equals(producto.getIdTipoAfectacion()))
                .map(AfectacionProductos::getNombreAfectacion)
                .findFirst().orElse("No encontrado");

        String nombreCategoria = listCategoriaProductos.stream()
                .filter(c -> c.getIdCategoria().equals(producto.getIdCategoria()))
                .map(CategoriaProductos::getNombreCategoria)
                .findFirst().orElse("No encontrado");

        prodAfec.setText(nombreAfectacion);
        prodCat.setText(nombreCategoria);
    }

    /** Busca un cliente por número de documento */
    @FXML
    private void buscarCliente() {
        String numDoc = numDocumento.getText();

        // Validación básica
        if (numDoc == null || numDoc.trim().isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Dato faltante", "Por favor ingrese un número de documento.");
            return;
        }

        if (listClientes == null || listClientes.isEmpty()) {
            mostrarAlerta(AlertType.ERROR, "Error de datos", "No hay clientes cargados en memoria.");
            return;
        }

        // Buscar cliente
        Cliente cliente = listClientes.stream()
                .filter(c -> c.getNumDocumento() != null && c.getNumDocumento().trim().equals(numDoc.trim()))
                .findFirst()
                .orElse(null);

        // Mostrar resultado
        if (cliente != null) {
            String nombreCompleto = cliente.getNombres() + " " + cliente.getApellidos();
            nomApeCliente.setText(nombreCompleto);
            mostrarAlerta(AlertType.INFORMATION, "Cliente encontrado", "Cliente: " + nombreCompleto);
        } else {
            nomApeCliente.setText("");
            mostrarAlerta(AlertType.WARNING, "No encontrado", "No se encontró un cliente con ese documento.");
        }
    }

    // =============================================================
    // MÉTODOS AUXILIARES
    // =============================================================

    /** Devuelve el ID del producto seleccionado */
    public long obtenerIdProductoSeleccionado() {
        Producto selected = listProductos.getSelectionModel().getSelectedItem();
        return (selected != null) ? selected.getIdProducto() : -1;
    }

    /** Devuelve el producto seleccionado */
    public Producto obtenerProductoSeleccionado() {
        return listProductos.getSelectionModel().getSelectedItem();
    }

    /** Muestra una alerta de tipo JavaFX */
    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
