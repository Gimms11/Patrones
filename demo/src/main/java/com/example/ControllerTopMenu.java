package com.example;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;

public class ControllerTopMenu {

    @FXML
    private BorderPane topMenu_total;

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