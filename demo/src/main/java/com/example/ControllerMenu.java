package com.example;

import java.io.IOException;

import javafx.fxml.FXML;

public class ControllerMenu {

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
        App.setRoot("Login");
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
