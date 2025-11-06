package controller;


import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class DashboardController {

    @FXML
    private WebView webView;

    public void initialize() {
        WebEngine engine = webView.getEngine();

        // 🔗 Reemplaza con tu enlace público de Power BI
        String powerBiUrl = "https://app.powerbi.com/view?r=eyJrIjoiNjc1OWYwYmYtMDBhMC00NTliLWEzNmEtYmY2NjE0YzY4OTUzIiwidCI6ImM0YTY2YzM0LTJiYjctNDUxZi04YmUxLWIyYzI2YTQzMDE1OCIsImMiOjR9" ;
        engine.load(powerBiUrl);
    }
}


