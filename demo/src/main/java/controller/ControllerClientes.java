package controller;

import DTO.Cliente;
import DTO.Departamento;
import DTO.Provincia;
import DTO.Distrito;
import DTO.TipoDocumento;
import service.UbigeoService;
import service.ClienteService;
import service.TipoDocumentoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;
import java.util.ArrayList;
import DAO.DAOFiltrarClientes;
import DAOImpl.DAOFiltrarClientesImpl;

public class ControllerClientes {

    // --- ComboBox del primer formulario ---
    @FXML private ComboBox<TipoDocumento> comboTipoDocumento;
    @FXML private ComboBox<Departamento> listDepartamento;
    @FXML private ComboBox<Provincia> listProvincia;
    @FXML private ComboBox<Distrito> listDistrito;

    // --- ComboBox del segundo formulario ---
    @FXML private ComboBox<TipoDocumento> comboTipoDocumento1;
    @FXML private ComboBox<Departamento> listDepartamento1;
    @FXML private ComboBox<Provincia> listProvincia1;
    @FXML private ComboBox<Distrito> listDistrito1;

    // --- Campos de filtrado ---
    @FXML private TextField txtFiltroDocumento;
    @FXML private TextField txtFiltroNombre;
    
    // --- TableView y sus columnas ---
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> col_id;
    @FXML private TableColumn<Cliente, String> col_nombres;
    @FXML private TableColumn<Cliente, String> col_apellidos;
    @FXML private TableColumn<Cliente, String> col_telefono;
    @FXML private TableColumn<Cliente, String> col_tipoDoc;
    @FXML private TableColumn<Cliente, String> col_numDoc;
    @FXML private TableColumn<Cliente, String> col_distrito;
    @FXML private TableColumn<Cliente, Hyperlink> col_modificar;

    // --- Datos de insercción del cliente ---

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtNumDoc;
    @FXML private TextArea txtDireccion;

    // --- Servicios ---
    private UbigeoService ubigeoService;
    private TipoDocumentoService tipoDocumentoService;
    private ClienteService clienteService;

    /**
     * Método que se ejecuta automáticamente al cargar la ventana (FXML)
     */
    @FXML
    public void initialize() {
        try {
            // Inicializar servicios (inyección manual)
            this.ubigeoService = new UbigeoService();
            this.tipoDocumentoService = new TipoDocumentoService();
            this.clienteService = new ClienteService();

            // Configurar la tabla
            configurarTabla();

            // Configurar ambos grupos de ComboBox
            configurarUbigeo(listDepartamento, listProvincia, listDistrito);
            configurarUbigeo(listDepartamento1, listProvincia1, listDistrito1);

            // Configurar ComboBox de tipos de documentos
            configurarCombo(comboTipoDocumento);
            configurarCombo(comboTipoDocumento1);

        } catch (Exception e) {
            mostrarError("Error de inicialización", "No se pudieron cargar los datos iniciales.", e.getMessage());
        }
    }

    /**
     * Configura el comportamiento progresivo de los ComboBox de ubicación
     */
    private void configurarUbigeo(
            ComboBox<Departamento> comboDep,
            ComboBox<Provincia> comboProv,
            ComboBox<Distrito> comboDist
    ) {
        try {
            // Cargar departamentos
            var departamentos = ubigeoService.cargarDepartamentos();
            comboDep.setItems(FXCollections.observableArrayList(departamentos));

            // Al seleccionar departamento → cargar provincias
            comboDep.setOnAction(e -> {
                comboProv.getItems().clear();
                comboDist.getItems().clear();

                Departamento dep = comboDep.getValue();
                if (dep != null) {
                    var provincias = ubigeoService.cargarProvincias(dep.getIdDepartamento());
                    comboProv.setItems(FXCollections.observableArrayList(provincias));
                }
            });

            // Al seleccionar provincia → cargar distritos
            comboProv.setOnAction(e -> {
                comboDist.getItems().clear();
                Provincia prov = comboProv.getValue();
                if (prov != null) {
                    var distritos = ubigeoService.cargarDistritos(prov.getIdProvincia());
                    comboDist.setItems(FXCollections.observableArrayList(distritos));
                }
            });
        } catch (Exception e) {
            mostrarError("Error al cargar ubicaciones", "No se pudieron cargar los datos de ubicación.", e.getMessage());
        }
    }

    /**
     * Configura un ComboBox de tipo de documento
     */
    private void configurarCombo(ComboBox<TipoDocumento> combo) {
        try {
            var documentos = tipoDocumentoService.cargarTipoDocumentos();
            combo.setItems(FXCollections.observableArrayList(documentos));
        } catch (Exception e) {
            mostrarError("Error al cargar documentos", "No se pudieron cargar los tipos de documento.", e.getMessage());
        }
    }

    /**
     * Obtiene el ID del distrito del primer formulario
     */
    public Long obtenerIdDistritoSeleccionado() {
        Distrito d = listDistrito.getValue();
        return (d != null) ? d.getIdDistrito() : null;
    }

    /**
     * Obtiene el ID del distrito del segundo formulario
     */
    public Long obtenerIdDistritoSeleccionado1() {
        Distrito d = listDistrito1.getValue();
        return (d != null) ? d.getIdDistrito() : null;
    }

    /**
     * Obtiene el ID del tipo de documento del primer ComboBox
     */
    public Long obtenerIdTipoDocumentoSeleccionado() {
        TipoDocumento doc = comboTipoDocumento.getValue();
        return (doc != null) ? doc.getIdDocumento() : null;
    }

    /**
     * Obtiene el ID del tipo de documento del segundo ComboBox
     */
    public Long obtenerIdTipoDocumentoSeleccionado1() {
        TipoDocumento doc = comboTipoDocumento1.getValue();
        return (doc != null) ? doc.getIdDocumento() : null;
    }

    /**
     * Método de ejemplo: insertar cliente (a completar)
     * Aquí puedes agregar validaciones antes de insertar a la BD.
     */
    @FXML
    public void insertarCliente() {
        
        // Validaciones
        String telefono = txtTelefono.getText();
        if (telefono.length() > 9) {
            mostrarAdvertencia("Datos incorrectos", "El teléfono no puede tener más de 9 caracteres.");
            return;
        }

        String correo = txtCorreo.getText();
        if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarAdvertencia("Datos incorrectos", "El formato del correo electrónico no es válido.");
            return;
        }

        Long idDocumento = obtenerIdTipoDocumentoSeleccionado();
        String numDoc = txtNumDoc.getText();

        if (idDocumento == 1 && numDoc.length() != 8) {
            mostrarAdvertencia("Datos incorrectos", "El DNI debe tener 8 caracteres.");
            return;
        }

        if (idDocumento == 2 && numDoc.length() != 11) {
            mostrarAdvertencia("Datos incorrectos", "El RUC debe tener 11 caracteres.");
            return;
        }

        Cliente cliente = new Cliente();

        cliente.setNombres(txtNombres.getText());
        cliente.setApellidos(txtApellidos.getText());
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setDireccion(txtDireccion.getText());
        cliente.setNumDocumento(numDoc);
        cliente.setIdDistrito(obtenerIdDistritoSeleccionado());
        cliente.setIdDocumento(idDocumento);
        
        try {
            clienteService.insertarCliente(cliente);
            mostrarInfo("Registro exitoso", "El cliente se ha registrado correctamente.");
            
            // Limpiar campos después de inserción exitosa
            limpiarCampos();
            
            // Actualizar la tabla
            cargarDatosTabla();
        } catch (Exception e) {
            mostrarError("Error al registrar", "No se pudo registrar el cliente.", e.getMessage());
        }
    }

    // --- MÉTODOS DE ALERTAS (ventanas emergentes) ---

    /**
     * Muestra una alerta de información
     */
    private void mostrarInfo(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de advertencia
     */
    private void mostrarAdvertencia(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de error con detalles
     */
    private void mostrarError(String titulo, String mensaje, String detalles) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(mensaje + "\n\nDetalles: " + detalles);
        alert.showAndWait();
    }

    /**
     * Configura la tabla de clientes y sus columnas
     */
    private void configurarTabla() {
        // Configurar las columnas
        col_id.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            Long id = cellData.getValue().getIdCliente();
            return new SimpleStringProperty(id != null ? id.toString() : "");
        });
        
        col_nombres.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getNombres() != null ? 
                cellData.getValue().getNombres() : ""
            );
        });
        
        col_apellidos.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getApellidos() != null ? 
                cellData.getValue().getApellidos() : ""
            );
        });
        
        col_telefono.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getTelefono() != null ? 
                cellData.getValue().getTelefono() : ""
            );
        });
        
        col_numDoc.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getNumDocumento() != null ? 
                cellData.getValue().getNumDocumento() : ""
            );
        });

        // Configurar columna de tipo documento
        col_tipoDoc.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getNombreTipoDocumento() != null ? 
                cellData.getValue().getNombreTipoDocumento() : ""
            );
        });

        // Configurar columna de distrito
        col_distrito.setCellValueFactory(cellData -> {
            if (cellData.getValue() == null) return new SimpleStringProperty("");
            return new SimpleStringProperty(
                cellData.getValue().getNombreDistrito() != null ? 
                cellData.getValue().getNombreDistrito() : ""
            );
        });

        // Configurar columna de modificar
        col_modificar.setCellFactory(param -> new TableCell<>() {
            private final Hyperlink link = new Hyperlink("Modificar");
            {
                link.setStyle("-fx-text-fill: blue;"); // Estilo para que parezca un enlace
            }

            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    link.setOnAction(event -> {
                        Cliente cliente = getTableView().getItems().get(getIndex());
                        abrirVentanaModificar(cliente);
                    });
                    setGraphic(link);
                }
            }
        });

        // Cargar datos iniciales
        cargarDatosTabla();
    }

    /**
     * Carga o recarga los datos en la tabla
     */
    private void cargarDatosTabla() {
        try {
            List<Cliente> clientes = clienteService.obtenerTodos();
            if (clientes == null) {
                clientes = new ArrayList<>();
            }
            tablaClientes.setItems(FXCollections.observableArrayList(clientes));
            tablaClientes.refresh(); // Forzar actualización de la vista
        } catch (Exception e) {
            mostrarError("Error al cargar datos", 
                        "No se pudieron cargar los datos de los clientes.", 
                        e.getMessage());
        }
    }

    /**
     * Limpia todos los campos del formulario de registro
     */
    private void limpiarCampos() {
        txtNombres.clear();
        txtApellidos.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtNumDoc.clear();
        txtDireccion.clear();

        limpiarCombo(comboTipoDocumento);
        limpiarCombo(listDepartamento);
        limpiarCombo(listProvincia);
        limpiarCombo(listDistrito);
    }

    private <T> void limpiarCombo(ComboBox<T> comboBox) {
        comboBox.getSelectionModel().clearSelection();
        comboBox.setValue(null);

        comboBox.setButtonCell(new javafx.scene.control.ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? comboBox.getPromptText() : item.toString());
            }
        });
    }

    /**
     * Abre la ventana de modificación de cliente
     */
    private void abrirVentanaModificar(Cliente cliente) {
        // TODO: Implementar ventana de modificación
        mostrarInfo("Modificar cliente", 
            "Se abrirá la ventana de modificación para el cliente:\n" + 
            cliente.getNombres() + " " + cliente.getApellidos());
    }

    /**
     * Método para filtrar clientes
     */
    @FXML
    private void filtrarClientes() {
        String numDoc = txtFiltroDocumento.getText();
        String nombre = txtFiltroNombre.getText();
        Long idDistrito = obtenerIdDistritoSeleccionado1();

        DAOFiltrarClientes filtroDAO = new DAOFiltrarClientesImpl();
        List<Cliente> clientesFiltrados = filtroDAO.filtrarPorMultiplesCriterios(
            numDoc.isEmpty() ? null : numDoc,
            nombre.isEmpty() ? null : nombre,
            idDistrito);

        tablaClientes.setItems(FXCollections.observableArrayList(clientesFiltrados));
    }
}
