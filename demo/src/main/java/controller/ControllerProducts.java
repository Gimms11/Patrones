package controller;

import DTO.AfectacionProductos;
import DTO.CategoriaProductos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import DAO.DAOTipoAfectacion;
import DAO.DAOCategoriaProducto;
import DAOImpl.DAOTipoAfectacionImpl;
import DAOImpl.DAOCategoriaProductoImpl;
import service.AfectacionService;
import service.CategoriaService;

public class ControllerProducts {

    @FXML private ComboBox<AfectacionProductos> listAfectacion;
    @FXML private ComboBox<AfectacionProductos> listAfectacion1;

    private AfectacionService afectacionService;

    //Categoria

    @FXML private ComboBox<CategoriaProductos> listCategoria;
    @FXML private ComboBox<CategoriaProductos> listCategoria1;

    private CategoriaService categoriaService;

    @FXML
    public void initialize() {
        // Inyección manual con buenas prácticas
        DAOTipoAfectacion repository = new DAOTipoAfectacionImpl();
        afectacionService = new AfectacionService(repository);

        // Llenar ambos ComboBox
        configurarCombo(listAfectacion);
        configurarCombo(listAfectacion1);


        // Inyección manual del repositorio
        DAOCategoriaProducto repositoryCat = new DAOCategoriaProductoImpl();
        categoriaService = new CategoriaService(repositoryCat);

        // Configurar ambos ComboBox
        configurarComboCat(listCategoria);
        configurarComboCat(listCategoria1);
    }

    private void configurarCombo(ComboBox<AfectacionProductos> combo) {
        var afectaciones = afectacionService.listarAfectaciones();
        combo.setItems(FXCollections.observableArrayList(afectaciones));
    }

    private void configurarComboCat(ComboBox<CategoriaProductos> combo) {
        var categorias = categoriaService.listarCategorias();
        combo.setItems(FXCollections.observableArrayList(categorias));
    }

    // Afectacion

    public Long obtenerIdAfectacionSeleccionada() {
        AfectacionProductos af = listAfectacion.getValue();
        return (af != null) ? af.getIdAfectacion() : null;
    }

    public Long obtenerIdAfectacionSeleccionada1() {
        AfectacionProductos af = listAfectacion1.getValue();
        return (af != null) ? af.getIdAfectacion() : null;
    }
    
    // Productos

    public Long obtenerIdCategoriaSeleccionada() {
        CategoriaProductos cat = listCategoria.getValue();
        return (cat != null) ? cat.getIdCategoria() : null;
    }

    public Long obtenerIdCategoriaSeleccionada1() {
        CategoriaProductos cat = listCategoria1.getValue();
        return (cat != null) ? cat.getIdCategoria() : null;
    }
}