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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
            comboDep.setPromptText("Seleccione un departamento");

            // Al seleccionar departamento → cargar provincias
            comboDep.setOnAction(e -> {
                comboProv.getItems().clear();
                comboDist.getItems().clear();

                Departamento dep = comboDep.getValue();
                if (dep != null) {
                    var provincias = ubigeoService.cargarProvincias(dep.getIdDepartamento());
                    comboProv.setItems(FXCollections.observableArrayList(provincias));
                    comboProv.setPromptText("Seleccione una provincia");
                }
            });

            // Al seleccionar provincia → cargar distritos
            comboProv.setOnAction(e -> {
                comboDist.getItems().clear();
                Provincia prov = comboProv.getValue();
                if (prov != null) {
                    var distritos = ubigeoService.cargarDistritos(prov.getIdProvincia());
                    comboDist.setItems(FXCollections.observableArrayList(distritos));
                    comboDist.setPromptText("Seleccione un distrito");
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
            combo.setPromptText("Seleccione un tipo de documento");
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
}
