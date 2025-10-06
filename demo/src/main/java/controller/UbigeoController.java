package controller;

import DTO.Departamento;
import DTO.Provincia;
import DTO.Distrito;
import repository.UbigeoRepository;
import repository.UbigeoRepositoryImpl;
import service.UbigeoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class UbigeoController {

    @FXML private ComboBox<Departamento> listDepartamento;
    @FXML private ComboBox<Provincia> listProvincia;
    @FXML private ComboBox<Distrito> listDistrito;

    private UbigeoService ubigeoService;

    @FXML
    public void initialize() {
        // Inyección manual (podrías usar un ServiceFactory si lo prefieres)
        UbigeoRepository repo = new UbigeoRepositoryImpl();
        this.ubigeoService = new UbigeoService(repo);

        cargarDepartamentos();

        listDepartamento.setOnAction(e -> {
            Departamento dep = listDepartamento.getValue();
            listProvincia.getItems().clear();
            listDistrito.getItems().clear();
            if (dep != null) {
                cargarProvincias(dep.getIdDepartamento());
            }
        });

        listProvincia.setOnAction(e -> {
            Provincia prov = listProvincia.getValue();
            listDistrito.getItems().clear();
            if (prov != null) {
                cargarDistritos(prov.getIdProvincia());
            }
        });
    }

    private void cargarDepartamentos() {
        var departamentos = ubigeoService.cargarDepartamentos();
        listDepartamento.setItems(FXCollections.observableArrayList(departamentos));
        listDepartamento.setPromptText("Seleccione un departamento");
    }

    private void cargarProvincias(Long idDepartamento) {
        var provincias = ubigeoService.cargarProvincias(idDepartamento);
        listProvincia.setItems(FXCollections.observableArrayList(provincias));
        listProvincia.setPromptText("Seleccione una provincia");
    }

    private void cargarDistritos(Long idProvincia) {
        var distritos = ubigeoService.cargarDistritos(idProvincia);
        listDistrito.setItems(FXCollections.observableArrayList(distritos));
        listDistrito.setPromptText("Seleccione un distrito");
    }

    public Long obtenerIdDistritoSeleccionado() {
        Distrito d = listDistrito.getValue();
        return (d != null) ? d.getIdDistrito() : null;
    }
}
