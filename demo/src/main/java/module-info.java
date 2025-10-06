module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens controller to javafx.fxml;
    exports controller;

    opens com.example to javafx.fxml;
    exports com.example;
}
