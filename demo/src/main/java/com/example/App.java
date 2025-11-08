package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.text.Font;

import java.io.IOException;

import DTO.Empresa;
import DTO.Usuario;
import Lectores.LectorEmpresa;
import Lectores.LectorSesion;

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
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Sistema de Facturación");
        stage.setScene(scene);
        stage.show();

        LectorEmpresa lector = new LectorEmpresa();
        Empresa emp = lector.leerArchivoJson(null);
        System.out.println("Empresa cargada: " + emp.getNombre() + ", RUC: " + emp.getRuc());

        LectorSesion lectorSesion = new LectorSesion();
        Usuario lec =lectorSesion.leerArchivoJson(null);
        System.out.println("Usuario cargado: " + lec.getUsername() + ", Rol: " + lec.getRol());
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