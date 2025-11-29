package com.example;

import java.io.IOException;


import javafx.fxml.FXMLLoader;

public class ControllerFactory {
    public static ControllerMenu crearControllerMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            ControllerFactory.class.getResource("/com/example/Menu.fxml")
        );
        loader.load();
        return loader.getController();
    }
    
    public static ControllerTopMenu crearControllerTopMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(
            ControllerFactory.class.getResource("/com/example/TopMenu.fxml")
        );
        loader.load();
        return loader.getController();
    }
}
