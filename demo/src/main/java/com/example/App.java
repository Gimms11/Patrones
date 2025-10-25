package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void init() {
        Font.loadFont(App.class.getResourceAsStream("/fonts/Roboto-Thin .ttf"), 10);
        Font.loadFont(App.class.getResourceAsStream("/fonts/Roboto-Regular.ttf"), 10);
        Font.loadFont(App.class.getResourceAsStream("/fonts/Roboto-Bold.ttf"), 10);
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("Dashboard"), 900, 700);
        //Asignar titulo a la ventana
        stage.setTitle("Sistema de Facturación");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}