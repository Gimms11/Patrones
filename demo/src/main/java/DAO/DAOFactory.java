package DAO;

import DAOImpl.PostgresDAOFactory;

public abstract class DAOFactory {

    // Tipos de bases de datos soportadas
    public static final int POSTGRES = 1;
    // ... otros tipos de bases de datos

    // Método para obtener la instancia de Factory
    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case POSTGRES:
                return new PostgresDAOFactory();
            default:
                return null;
        }
    }

    // Método por defecto que retorna PostgresDAOFactory
    public static DAOFactory getDAOFactory() {
        return getDAOFactory(POSTGRES);
    }

    public abstract DAOUbigeo getUbigeoDAO();
    public abstract DAOCategoriaProducto getCategoriaProductoDAO();
    public abstract DAOTipoAfectacion getAfectacionDAO();
    public abstract DAOTipoDocumento getTipoDocumentoDAO();
    public abstract DAOTipoComprobante getTipoComprobanteDAO();
    public abstract DAOProducto getProductoDAO();
    public abstract DAOCliente getClienteDAO();
    public abstract DAOComprobante getComprobanteDAO();
    public abstract DAOMedioPago getMedioPagoDAO();
}
