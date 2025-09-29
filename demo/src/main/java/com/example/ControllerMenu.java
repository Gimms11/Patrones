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
        App.setRoot("Clients");
    }
    @FXML
    private void goToGenFactures() throws IOException {
        App.setRoot("GenFactures");
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
}
