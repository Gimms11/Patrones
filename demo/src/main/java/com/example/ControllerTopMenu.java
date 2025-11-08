package com.example;

import javafx.scene.control.Label;

import DTO.Usuario;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import service.LectorService;
import javafx.scene.Node;

public class ControllerTopMenu {

    @FXML
    private BorderPane topMenu_total;

    @FXML private Label txtUserName;
    @FXML private Label txtRol;

    @FXML
    private void btnMinimizar() {
        Stage stage = (Stage) topMenu_total.getScene().getWindow();
        stage.setIconified(true); // Minimiza la ventana
    }

    @FXML
    private void btnCerrar() {
        Stage stage = (Stage) topMenu_total.getScene().getWindow();
        stage.close(); // Cierra la ventana
    }

    // Variables para el arrastre
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        // Hacer que la barra de título permita arrastrar la ventana
        topMenu_total.setOnMousePressed(this::handleMousePressed);
        topMenu_total.setOnMouseDragged(this::handleMouseDragged);

        LectorService lectorService = new LectorService();
        Usuario usuarioActual = lectorService.leerUsuario();

        txtUserName.setText(usuarioActual.getUsername());
        txtRol.setText(usuarioActual.getRol());
    }

    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
}