package pe.utp.facturacion.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pe.utp.facturacion.persistence.ConexionBD;
import pe.utp.facturacion.persistence.dao.DAOTipoImpuestos;
import pe.utp.facturacion.model.TipoImpuestos;

public class DAOTipoImpuestosImpl implements DAOTipoImpuestos {
    private static final String SQLlista="Select * from tipoImpuesto";
    @Override
    public List<TipoImpuestos> listaTipoImpuesto() {

        List<TipoImpuestos> listaImpuestos=new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLlista);
            ResultSet res=ps.executeQuery();  
            while (res.next()) {
                TipoImpuestos ti = new TipoImpuestos(
                res.getLong("idImpuesto"),
                res.getString("nombre"),
                res.getBigDecimal("procImpuesto"));
                listaImpuestos.add(ti);
            }          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoImpuesto();
    }

}
