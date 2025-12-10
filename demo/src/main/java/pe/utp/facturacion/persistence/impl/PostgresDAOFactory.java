package pe.utp.facturacion.persistence.impl;

import pe.utp.facturacion.persistence.dao.*;

public class PostgresDAOFactory extends DAOFactory {

    @Override
    public DAOUbigeo getUbigeoDAO() {
        System.out.println("[ABSTRACT FACTORY] Creando DAOUbigeoImpl (PostgreSQL)");
        System.out.println("[GRASP: Creator] PostgresDAOFactory crea DAOs específicos de PostgreSQL");
        return new DAOUbigeoImpl();
    }

    @Override
    public DAOCategoriaProducto getCategoriaProductoDAO() {
        System.out.println("[ABSTRACT FACTORY] Creando DAOCategoriaProductoImpl (PostgreSQL)");
        return new DAOCategoriaProductoImpl();
    }

    @Override
    public DAOTipoDocumento getTipoDocumentoDAO() {
        System.out.println("[ABSTRACT FACTORY] Creando DAOTipoDocumentoImpl (PostgreSQL)");
        return new DAOTipoDocumentoImpl();
    }

    @Override
    public DAOProducto getProductoDAO() {
        System.out.println("[ABSTRACT FACTORY] Creando DAOProductoImpl (PostgreSQL)");
        return new DAOProductoImpl();
    }

    @Override
    public DAOCliente getClienteDAO() {
        System.out.println("[ABSTRACT FACTORY] Creando DAOClienteImpl (PostgreSQL)");
        return new DAOClienteImpl();
    }

    @Override
    public DAOComprobante getComprobanteDAO() {
        System.out.println("[ABSTRACT FACTORY] Creando DAOComprobanteImpl (PostgreSQL)");
        return new DAOComprobanteImpl();
    }

    @Override
    public DAOTipoAfectacion getAfectacionDAO() {
        return new DAOTipoAfectacionImpl();
    }

    @Override
    public DAOMedioPago getMedioPagoDAO() {
        return new DAOMedioPagoImpl();
    }

    @Override
    public DAOTipoComprobante getTipoComprobanteDAO() {
        return new DAOTipoComprobanteImpl();
    }
}
