module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.web;
    requires itextpdf;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
    requires java.mail;
    

    opens controller to javafx.fxml;
    exports controller;

    opens DTO to com.google.gson;
    exports DTO;

    opens com.example to javafx.fxml;
    exports com.example;
}

