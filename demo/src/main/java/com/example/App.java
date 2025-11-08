package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.ClienteService;
import service.ComprobanteService;
import service.LectorService;
import javafx.scene.text.Font;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAOImpl.DAODetalleComprobanteImpl;
import DTO.Cliente;
import DTO.Comprobante;
import DTO.DetalleComprobante;
import DTO.Empresa;
import DTO.Usuario;
import Interfaces.GeneradorPDFBuilder;
import Lectores.LectorEmpresa;
import Lectores.LectorSesion;
import PDFGenerator.GeneradorBoletaPDF;
import PDFGenerator.GeneradorFacturaPDF;
import PDFGenerator.GeneradorPDFDirector;

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

        LectorService lectorServicio = new LectorService();

        Empresa emp = lectorServicio.leerEmpresa();
        System.out.println("Empresa cargada: " + emp.getNombre() + ", RUC: " + emp.getRuc());
        Usuario lec = lectorServicio.leerUsuario();
        System.out.println("Usuario cargado: " + lec.getUsername() + ", Rol: " + lec.getRol());

        try {
            ClienteService clienteService = new ClienteService();

            ComprobanteService comprobanteService = new ComprobanteService();
            Comprobante comp = comprobanteService.listarComprobante().get(10);

            DAODetalleComprobanteImpl dao = new DAODetalleComprobanteImpl();
            List<DetalleComprobante> detalles = dao.listarDetallesPorComprobante(comp.getIdComprobante());
            Cliente cli = clienteService.obtenerPorId(comp.getIdCliente());

            GeneradorPDFDirector director = new GeneradorPDFDirector();

            // FACTURA
            GeneradorPDFBuilder facturaBuilder = new GeneradorFacturaPDF();
            director.setBuilder(facturaBuilder);
            String rutaFactura = "demo\\src\\main\\resources\\com\\example\\PDFs\\factura_F001-000123.pdf";
            director.generarComprobantePDF(emp, cli, comp, detalles, rutaFactura);

            // BOLETA
            GeneradorPDFBuilder boletaBuilder = new GeneradorBoletaPDF();
            director.setBuilder(boletaBuilder);
            String rutaBoleta = "demo\\src\\main\\resources\\com\\example\\PDFs\\boleta_B001-000123.pdf";
            director.generarComprobantePDF(emp, cli, comp, detalles, rutaBoleta);

            System.out.println("PDFs generados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    
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