module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.web;

    opens controller to javafx.fxml;
    exports controller;

    opens com.example to javafx.fxml;
    exports com.example;
}

