package pe.utp.facturacion.core;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

import pe.utp.facturacion.controller.ControllerMenu;
import pe.utp.facturacion.controller.ControllerTopMenu;

public class ControllerFactory {
    public static ControllerMenu crearControllerMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                ControllerFactory.class.getResource("/pe/utp/facturacion/Menu.fxml"));
        loader.load();
        return loader.getController();
    }

    public static ControllerTopMenu crearControllerTopMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                ControllerFactory.class.getResource("/pe/utp/facturacion/TopMenu.fxml"));
        loader.load();
        return loader.getController();
    }
}
