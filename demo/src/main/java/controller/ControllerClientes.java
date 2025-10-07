package controller;

import DTO.Departamento;
import DTO.Provincia;
import DTO.TipoDocumento;
import DTO.Distrito;
import repository.TipoDocumentoRepository;
import repository.TipoDocumentoRepositoryImpl;
import repository.UbigeoRepository;
import repository.UbigeoRepositoryImpl;
import service.TipoDocumentoService;
import service.UbigeoService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ControllerClientes {

    // ComboBox del primer formulario
    @FXML private ComboBox<TipoDocumento> comboTipoDocumento;
    // ComboBox del segundo formulario
    @FXML private ComboBox<TipoDocumento> comboTipoDocumento1;

    private TipoDocumentoService tipoDocumentoService;

    // Primer formulario
    @FXML private ComboBox<Departamento> listDepartamento;
    @FXML private ComboBox<Provincia> listProvincia;
    @FXML private ComboBox<Distrito> listDistrito;

    // Segundo formulario
    @FXML private ComboBox<Departamento> listDepartamento1;
    @FXML private ComboBox<Provincia> listProvincia1;
    @FXML private ComboBox<Distrito> listDistrito1;

    private UbigeoService ubigeoService;

    @FXML
    public void initialize() {
        // Inyección manual del repositorio
        UbigeoRepository repo = new UbigeoRepositoryImpl();
        this.ubigeoService = new UbigeoService(repo);

        // Configuramos ambos grupos de ComboBox
        configurarUbigeo(listDepartamento, listProvincia, listDistrito);
        configurarUbigeo(listDepartamento1, listProvincia1, listDistrito1);

        // Inyección manual (repository + service)
        TipoDocumentoRepository repoDoc = new TipoDocumentoRepositoryImpl();
        this.tipoDocumentoService = new TipoDocumentoService(repoDoc);

        // Configuramos ambos comboBox
        configurarCombo(comboTipoDocumento);
        configurarCombo(comboTipoDocumento1);
    }

    /**
     * Configura el comportamiento progresivo de un trío de ComboBox
     */
    private void configurarUbigeo(
            ComboBox<Departamento> comboDep,
            ComboBox<Provincia> comboProv,
            ComboBox<Distrito> comboDist
    ) {
        // Cargar departamentos
        comboDep.setItems(FXCollections.observableArrayList(ubigeoService.cargarDepartamentos()));
        

        // Al seleccionar departamento
        comboDep.setOnAction(e -> {
            Departamento dep = comboDep.getValue();
            comboProv.getItems().clear();
            comboDist.getItems().clear();
            if (dep != null) {
                var provincias = ubigeoService.cargarProvincias(dep.getIdDepartamento());
                comboProv.setItems(FXCollections.observableArrayList(provincias));
            }
        });

        // Al seleccionar provincia
        comboProv.setOnAction(e -> {
            Provincia prov = comboProv.getValue();
            comboDist.getItems().clear();
            if (prov != null) {
                var distritos = ubigeoService.cargarDistritos(prov.getIdProvincia());
                comboDist.setItems(FXCollections.observableArrayList(distritos));
            }
        });
    }

    /**
     * Retorna el ID del distrito seleccionado del primer formulario.
     */
    public Long obtenerIdDistritoSeleccionado() {
        Distrito d = listDistrito.getValue();
        return (d != null) ? d.getIdDistrito() : null;
    }

    /**
     * Retorna el ID del distrito seleccionado del segundo formulario.
     */
    public Long obtenerIdDistritoSeleccionado1() {
        Distrito d = listDistrito1.getValue();
        return (d != null) ? d.getIdDistrito() : null;
    }


    /**
     * Configura un ComboBox de tipo de documento (reutilizable)
     */
    private void configurarCombo(ComboBox<TipoDocumento> combo) {
        var documentos = tipoDocumentoService.cargarTipoDocumentos();
        combo.setItems(FXCollections.observableArrayList(documentos));
        combo.setPromptText("Seleccione un tipo de documento");
    }

    /**
     * Devuelve el ID del tipo de documento seleccionado del primer combo
     */
    public Long obtenerIdTipoDocumentoSeleccionado() {
        TipoDocumento doc = comboTipoDocumento.getValue();
        return (doc != null) ? doc.getIdDocumento() : null;
    }

    /**
     * Devuelve el ID del tipo de documento seleccionado del segundo combo
     */
    public Long obtenerIdTipoDocumentoSeleccionado1() {
        TipoDocumento doc = comboTipoDocumento1.getValue();
        return (doc != null) ? doc.getIdDocumento() : null;
    }
}
