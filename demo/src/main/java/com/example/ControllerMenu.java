package com.example;

import java.io.IOException;
import java.util.Optional;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;

public class ControllerMenu {
    @FXML private FontIcon das;
    @FXML private FontIcon agr1;
    @FXML private FontIcon agr2;
    @FXML private FontIcon agr3;
    @FXML private FontIcon historial;


    @FXML
    public void initialize() {
        Tooltip tooltip = new Tooltip("Dashboard");
        Tooltip.install(das, tooltip);
        tooltip = new Tooltip("Generar Comprobantes");
        Tooltip.install(agr1, tooltip);
        tooltip = new Tooltip("Agregar Clientes");
        Tooltip.install(agr2, tooltip);
        tooltip = new Tooltip("Agregar Productos");
        Tooltip.install(agr3, tooltip);
        tooltip = new Tooltip("Historial de Comprobantes");
        Tooltip.install(historial, tooltip);
    }

    @FXML
    private void goToDashboard() throws IOException {
        App.setRoot("Dashboard");
    }
    @FXML
    private void goToClients() throws IOException {
        App.setRoot("GenClientes");
    }
    @FXML
    private void goToGenFactures() throws IOException {
        App.setRoot("GenFactures");
    }
    @FXML
    private void gotoGenProducts() throws IOException {
        App.setRoot("GenProducts");
    }
    @FXML
    private void goToHistory() throws IOException {
        App.setRoot("History");
    }
    @FXML
    private void goToConfigurations() throws IOException {
        App.setRoot("Configurations");
    }
    @FXML
    private void goToSingOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Está seguro que desea cerrar sesión?");
        alert.setContentText("Se perderá la sesión actual.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            App.setRoot("Login");
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
