package DAO;

import DAOImpl.PostgresDAOFactory;

public abstract class DAOFactory {

    // Método para obtener la conexión (opcional, si lo necesitas en los DAO)
    public static DAOFactory getDAOFactory() {
        return new PostgresDAOFactory();
    }

    public abstract DAOUbigeo getUbigeoDAO();
    public abstract DAOCategoriaProducto getCategoriaProductoDAO();
    public abstract DAOTipoAfectacion getAfectacionDAO();
    public abstract DAOTipoDocumento getTipoDocumentoDAO();
    public abstract DAOProducto getProductoDAO();
    public abstract DAOCliente getClienteDAO();
    public abstract DAOComprobante getComprobanteDAO();
}
