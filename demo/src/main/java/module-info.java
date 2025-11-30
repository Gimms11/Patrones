module pe.utp.facturacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires javafx.web;
    requires itextpdf;
    requires java.desktop;
    requires org.kordamp.ikonli.javafx;
    requires java.mail;

    // Controllers - necesarios para JavaFX reflection
    opens pe.utp.facturacion.controller to javafx.fxml;

    exports pe.utp.facturacion.controller;

    // Core - aplicación principal
    opens pe.utp.facturacion.core to javafx.fxml;

    exports pe.utp.facturacion.core;

    // Model - DTOs para serialización
    opens pe.utp.facturacion.model to com.google.gson;

    exports pe.utp.facturacion.model;

    // Otros paquetes que pueden necesitar exportación
    exports pe.utp.facturacion.service;
    exports pe.utp.facturacion.persistence.dao;
    exports pe.utp.facturacion.persistence.impl;
    exports pe.utp.facturacion.patterns.builder;
    exports pe.utp.facturacion.patterns.strategy;
    exports pe.utp.facturacion.patterns.strategy.json;
    exports pe.utp.facturacion.patterns.adapter;
    exports pe.utp.facturacion.ui.loading;
}
