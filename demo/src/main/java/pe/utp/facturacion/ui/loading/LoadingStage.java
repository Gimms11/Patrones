package pe.utp.facturacion.ui.loading;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoadingStage {
    private Stage stage;

    public LoadingStage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoadingView.fxml"));
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }

    public void close() {
        stage.close();
    }
}
