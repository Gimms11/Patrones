package controller;

import DTO.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import service.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ControllerFacturas {

    // === ELEMENTOS DE LA VISTA ===
    @FXML private ComboBox<Producto> listProductos;
    @FXML private ComboBox<TipoDocumento> listTipoDoc;
    @FXML private ComboBox<MedioPago> listMediosPago;
    @FXML private ComboBox<TipoComprobante> listTipoComp;

    @FXML private TextField prodPrecio;
    @FXML private TextField prodStock;
    @FXML private TextField prodCat;
    @FXML private TextField prodAfec;
    @FXML private TextField prodUnidad;
    @FXML private TextField prodImp;

    @FXML private TextField numDocumento;
    @FXML private TextField nomApeCliente;

    @FXML private TextField txtNumSerie;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtFechaEmision;

    // === SERVICIOS Y LISTAS ===
    private ProductoService productoService;
    private AfectacionService afectacionService;
    private CategoriaService categoriaService;
    private ClienteService clienteService;
    private TipoDocumentoService tipoDocumentoService;
    private MedioPagoService medioPagoService;
    private TipoComprService tipoComprobanteService;
    private ComprobanteService comprobanteService;

    private List<AfectacionProductos> listAfectacionProductos;
    private List<CategoriaProductos> listCategoriaProductos;
    private List<Cliente> listClientes;
    private List<Comprobante> listComprobantes;

    private List<DetalleComprobante> listaDetalle;

    // =============================================================
    // INITIALIZE
    // =============================================================
    @FXML
    public void initialize() {
        try {
            //Generar lista vacia de detalles
            listaDetalle = new ArrayList<>();

            // Inyección manual de servicios
            this.productoService = new ProductoService();
            this.afectacionService = new AfectacionService();
            this.categoriaService = new CategoriaService();
            this.tipoDocumentoService = new TipoDocumentoService();
            this.clienteService = new ClienteService();
            this.medioPagoService = new MedioPagoService();
            this.tipoComprobanteService = new TipoComprService();
            this.comprobanteService = new ComprobanteService();

            // Cargar datos base
            this.listAfectacionProductos = afectacionService.listarAfectaciones();
            this.listCategoriaProductos = categoriaService.listarCategorias();
            this.listClientes = clienteService.obtenerTodos();
            this.listComprobantes = comprobanteService.listarComprobante();

            // Configurar combos
            configurarComboProd(listProductos);
            configurarComboDoc(listTipoDoc);
            configurarComboMedioPago(listMediosPago);
            configurarComboTipoComprobante(listTipoComp);

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

    /** Configura el ComboBox de medios de Pago del servicio */
    private void configurarComboMedioPago(ComboBox<MedioPago> combo) {
        var tipos = medioPagoService.listarMedioPagos();
        combo.setItems(FXCollections.observableArrayList(tipos));
        combo.setPromptText("Seleccione Medio De Pago");
    }

    /** Configura el ComboBox de Tipos de Comprobantes */
    private void configurarComboTipoComprobante(ComboBox<TipoComprobante> combo) {
        var tipos = tipoComprobanteService.listarTipoComprobantes();
        combo.setItems(FXCollections.observableArrayList(tipos));
        combo.setPromptText("Seleccione Tipo De Comprobante");
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
        prodImp.setText(producto.getIdTipoAfectacion() == 1? "IGV" : "Ninguno");
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

    public MedioPago obtenerMedioPagoSeleccionado() {
        return listMediosPago.getSelectionModel().getSelectedItem();
    }

    /** Muestra una alerta de tipo JavaFX */
    private void mostrarAlerta(AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String formatBigDecimal(BigDecimal value) {
        if (value == null) {
            return "0.00";
        }
        return value.setScale(2, RoundingMode.HALF_UP).toString();
    }

    // Metodos Generación de Comprobante

    /**
     * Maneja el evento del botón Agregar.
     * Valida los campos necesarios antes de proceder con la generación del comprobante.
     */
    @FXML
    private void btnAgregar() {
        // Validar que haya un producto seleccionado
        if (obtenerProductoSeleccionado() == null) {
            mostrarAlerta(AlertType.WARNING, "Validación", "Por favor seleccione un producto");
            return;
        }

        // Validar que se haya ingresado una cantidad
        if (txtCantidad.getText() == null || txtCantidad.getText().trim().isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Validación", "Por favor ingrese una cantidad");
            return;
        }

        // Validar que la cantidad sea un número válido
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            if (cantidad <= 0) {
                mostrarAlerta(AlertType.WARNING, "Validación", "La cantidad debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(AlertType.WARNING, "Validación", "La cantidad debe ser un número válido");
            return;
        }

        // Validar que se haya seleccionado un cliente
        if (nomApeCliente.getText() == null || nomApeCliente.getText().trim().isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Validación", "Por favor seleccione un cliente");
            return;
        }

        // Proceder con la generación del comprobante
        generarSerie();
        agregarDetalle();
        mostrarDetallesEnAlerta();
    }

    private void generarSerie() {
        String serie;
        
        // Validar si hay comprobantes existentes
        if (listComprobantes == null || listComprobantes.isEmpty()) {
            serie = "FG-1"; // Serie inicial
            System.out.println("DEBUG: No hay comprobantes previos, iniciando serie en " + serie);
        } else {
            // Obtener el último comprobante
            Comprobante ultimoComprobante = listComprobantes.get(listComprobantes.size() - 1);
            String seriePrev = ultimoComprobante.getSerie();
            System.out.println("DEBUG: Última serie encontrada: " + seriePrev);
            
            try {
                String[] parts = seriePrev.split("-");
                if (parts.length == 2) {
                    int numero = Integer.parseInt(parts[1]) + 1;
                    serie = parts[0] + "-" + numero;
                    System.out.println("DEBUG: Nueva serie generada: " + serie);
                } else {
                    serie = "FG-1"; // Serie por defecto si el formato no es válido
                    System.out.println("DEBUG: Formato de serie inválido, reiniciando a " + serie);
                }
            } catch (NumberFormatException e) {
                serie = "FG-1"; // En caso de error al parsear el número
                System.out.println("DEBUG: Error al parsear número de serie, reiniciando a " + serie);
                e.printStackTrace();
            }
        }

        txtFechaEmision.setText(java.time.LocalDate.now().toString());
        txtNumSerie.setText(serie);
    }

    /**
     * Agrega un nuevo detalle al comprobante actual.
     * Realiza cálculos de subtotales y validaciones de stock.
     */
    private void agregarDetalle() {
        DetalleComprobante detalle = new DetalleComprobante();
        Producto producto = obtenerProductoSeleccionado();

        // Validación del producto y su stock
        if (producto == null) {
            mostrarAlerta(AlertType.WARNING, "Error", "No se ha seleccionado ningún producto");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException e) {
            mostrarAlerta(AlertType.WARNING, "Error", "La cantidad ingresada no es válida");
            return;
        }

        if (producto.getStock() < cantidad) {
            mostrarAlerta(AlertType.WARNING, "Stock insuficiente", 
                "Stock disponible: " + producto.getStock() + "\n" +
                "Cantidad solicitada: " + cantidad);
            return;
        }

        detalle.setCantidadProductos(Integer.parseInt(txtCantidad.getText()));
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setIdProducto(producto.getIdProducto());

        //Seleccionar el idDetalle más alto y sumarle 1
        Long idDetalle = listComprobantes.stream()
                .mapToLong(Comprobante::getIdComprobante)
                .max()
                .orElse(0L) + 1;

        detalle.setIdComprobante(idDetalle);
        detalle.setIdImpuesto(producto.getIdTipoAfectacion()==1? 2L : 1L);


        //Subtotal
        detalle.setSubtotal(detalle.getPrecioUnitario().multiply(new java.math.BigDecimal(detalle.getCantidadProductos())));
        //Total
        double tasa = (producto.getIdTipoAfectacion()==2? 0.0 : 0.18) + 1.0;
        detalle.setTotal(detalle.getSubtotal().multiply(new java.math.BigDecimal(tasa)));

        listaDetalle.add(detalle);
    }

    public void mostrarDetallesEnAlerta(){
        for (DetalleComprobante detalle : listaDetalle) {
            StringBuilder detallesStr = new StringBuilder("Detalles del Comprobante:\n");
            detallesStr.append("ID Producto: ").append(detalle.getIdProducto())
                    .append("\n")
                    .append(", Cantidad: ").append(detalle.getCantidadProductos())
                    .append("\n")
                    .append(", Precio Unitario: ").append(formatBigDecimal(detalle.getPrecioUnitario()))
                    .append("\n")
                    .append(", Subtotal: ").append(formatBigDecimal(detalle.getSubtotal()))
                    .append("\n")
                    .append(", Total: ").append(formatBigDecimal(detalle.getTotal()))
                    .append("\n")
                    .append("\n");
            mostrarAlerta(AlertType.INFORMATION, "Detalle " + detalle.getIdProducto(), detallesStr.toString());
        }
    }   

    @FXML
    private void btnGenerarComprobante(){
        btnGuardarComprobante();
        System.out.println("DEBUG: Comprobante guardado, actualizando lista de comprobantes");
        // Actualizar la lista de comprobantes antes de mostrar
        this.listComprobantes = comprobanteService.listarComprobante();
        System.out.println("DEBUG: Lista de comprobantes actualizada, cantidad: " + (listComprobantes != null ? listComprobantes.size() : 0));
        mostrarComprobanteEnAlerta();

        listaDetalle = new ArrayList<>();
    }

    /**
     * Guarda el comprobante actual con todos sus detalles en la base de datos.
     * Realiza validaciones necesarias antes de guardar.
     */
    private void btnGuardarComprobante() {
        // Validar que haya detalles para guardar
        if (listaDetalle == null || listaDetalle.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Validación", "No hay productos agregados al comprobante");
            return;
        }

        // Validar que se haya seleccionado el tipo de comprobante
        if (listTipoComp.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(AlertType.WARNING, "Validación", "Seleccione un tipo de comprobante");
            return;
        }

        // Validar que se haya seleccionado el medio de pago
        if (listMediosPago.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(AlertType.WARNING, "Validación", "Seleccione un medio de pago");
            return;
        }

        Comprobante comprobante = new Comprobante();

        // Generar nuevo ID de comprobante
        comprobante.setIdComprobante(listComprobantes.stream()
                .mapToLong(Comprobante::getIdComprobante)
                .max()
                .orElse(0L) + 1);
        comprobante.setFechaEmision(java.time.LocalDate.now());
        comprobante.setSerie(txtNumSerie.getText());

        //Calcular la suma de los totales de los detalles
        BigDecimal sumaTotal = listaDetalle.stream()
                .map(DetalleComprobante::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        comprobante.setTotalFinal(sumaTotal);

        //Calcular la suma de los subtotales de los detalles
        BigDecimal sumaDevengado = listaDetalle.stream()
                .map(DetalleComprobante::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        comprobante.setDevengado(sumaDevengado);

        //Obtenemos el Cliente por su numDocumento
        Cliente cliente = listClientes.stream()
                .filter(c -> c.getNumDocumento() != null && c.getNumDocumento().trim().equals(numDocumento.getText().trim()))
                .findFirst()
                .orElse(null);

        comprobante.setDireccionEnvio(cliente.getDireccion());
        comprobante.setIdTipoComprobante(listTipoComp.getSelectionModel().getSelectedItem().getIdTipoComprobante());
        comprobante.setIdMedioPago(listMediosPago.getSelectionModel().getSelectedItem().getIdMedioPago());
        comprobante.setIdCliente(cliente.getIdCliente());
        comprobante.setIdUsuario(1L);

        comprobanteService.subirComprobante(comprobante);
    }

    //Mostrar Comprobantes en una alerta
    private void mostrarComprobanteEnAlerta(){
        for (Comprobante c : listComprobantes) {
            StringBuilder comprobanteStr = new StringBuilder("Comprobante Generado:\n");
            comprobanteStr.append("ID Comprobante: ").append(c.getIdComprobante())
                    .append("\n")
                    .append(", Fecha Emisión: ").append(c.getFechaEmision())
                    .append("\n")
                    .append(", Serie: ").append(c.getSerie())
                    .append("\n")
                    .append(", Total Final: ").append(formatBigDecimal(c.getTotalFinal()))
                    .append("\n")
                    .append(", Devengado: ").append(formatBigDecimal(c.getDevengado()))
                    .append("\n")
                    .append(", Dirección Envío: ").append(c.getDireccionEnvio())
                    .append("\n");
            mostrarAlerta(AlertType.INFORMATION, "Comprobante " + c.getIdComprobante(), comprobanteStr.toString());
        }
    }

}
