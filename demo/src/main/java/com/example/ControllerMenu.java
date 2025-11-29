package com.example;

import java.io.IOException;
import java.util.Optional;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import service.AutenticacioService;
import service.PermisosNavegacion;

public class ControllerMenu {
    @FXML private FontIcon das;
    @FXML private FontIcon agr1;
    @FXML private FontIcon agr2;
    @FXML private FontIcon agr3;
    @FXML private FontIcon historial;

    private PermisosNavegacion permisosnav;
    private AutenticacioService servicioAut;

    @FXML
    public void initialize() {
        permisosnav = PermisosNavegacion.getInstance();
        servicioAut = AutenticacioService.getInstance();

        if (servicioAut.getUsuarioActual() == null) {
            redirigirALogin();
            return;
        }

        configurarTooltip();
        aplicarPermisos();
    }

    @FXML
    private void goToDashboard() throws IOException {
        navegarConValidacion("Dashboard");
    }
    @FXML
    private void goToClients() throws IOException {
        navegarConValidacion("GenClientes");
    }
    @FXML
    private void goToGenFactures() throws IOException {
        navegarConValidacion("GenFactures");
    }
    @FXML
    private void gotoGenProducts() throws IOException {
        navegarConValidacion("GenProducts");
    }
    @FXML
    private void goToHistory() throws IOException {
        navegarConValidacion("History");
    }
    @FXML
    private void goToConfigurations() throws IOException {
        navegarConValidacion("Configurations");
    }
    @FXML
    private void goToSingOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Está seguro que desea cerrar sesión?");
        alert.setContentText("Se perderá la sesión actual.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            servicioAut.cerrarSesion();
            App.setRoot("Login");
        }
    }

    private void configurarTooltip(){
        Tooltip.install(das, new Tooltip("Dashboard"));
        Tooltip.install(agr1, new Tooltip("Generar Comprobantes"));
        Tooltip.install(agr2, new Tooltip("Agregar Clientes"));
        Tooltip.install(agr3, new Tooltip("Agregar Productos"));
        Tooltip.install(historial, new Tooltip("Historial de Comprobantes"));
    }

    private void aplicarPermisos(){
        boolean esAdmin = servicioAut.esAdmin();
        boolean esEmisor = servicioAut.esEmisor();

        agr3.setVisible(esAdmin);
        agr3.setManaged(esAdmin);

        boolean puedeEmitir = esAdmin || esEmisor;
        agr1.setVisible(puedeEmitir);
        agr1.setManaged(puedeEmitir);
        agr2.setVisible(puedeEmitir);
        agr2.setManaged(puedeEmitir);
        historial.setVisible(true);
    }

    private void navegarConValidacion (String pantalla) {
        try {
            permisosnav.navegarA(pantalla);;
        } catch (SecurityException e) {
            mostrarAlertaError("Acceso Denegado", 
                "No tiene permisos para acceder a: " + pantalla + "\n\n" +
                "Rol requerido: " + obtenerRolesRequeridos(pantalla));
        } catch (IOException e) {
            mostrarAlertaError("Error de Navegación", 
                "No se pudo cargar la pantalla: " + pantalla);
        }
    }

    private String obtenerRolesRequeridos(String pantalla) {
        switch (pantalla) {
            case "GenProducts":
            case "Configurations":
                return "ADMIN";
            case "GenClientes":
            case "GenFactures":
                return "ADMIN, EMISOR";
            case "History":
            case "Dashboard":
                return "ADMIN, EMISOR, LECTOR";
            default:
                return "No definido";
        }
    }

    private void mostrarAlertaError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void redirigirALogin() {
        try {
            App.setRoot("Login");
        } catch (IOException e) {
            System.exit(1);
        }
    }
    /*
     * 
     * metodorealizarClculos(idProducto, cantidad){
     *  // Obtener el producto desde la base de datos
     * DAOProducto daoMIO = new DAOProductoImpl();
     * Producto producto = daoMIO.buscarProducto(idProducto);
     *  if (producto == null) {
     *   System.out.println("Producto no encontrado");
     * } else {
     *   // Realizar los cálculos
     * BigDecimal precioUnitario = producto.getPrecio();
     * BigDecimal cantidadBD = BigDecimal.valueOf(cantidad);
     * BigDecimal subtotal = precioUnitario.multiply(cantidadBD);
     *  BigDecimal impuesto;
     * if (producto.getIdTipoAfectacion() == 1) { // Gravado
     *   impuesto = TipoImpuesto.getInstance().getValor().multiply(subtotal);
     * } else {
     *  impuesto = BigDecimal.ZERO; // Exonerado o no gravado
     * }
     * BigDecimal total = subtotal.add(impuesto);
     * 
     * }
     * 
     */
}
