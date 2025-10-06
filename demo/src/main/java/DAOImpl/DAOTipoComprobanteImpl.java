package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOTipoComprobante;
import DTO.TipoComprobante;

public class DAOTipoComprobanteImpl implements DAOTipoComprobante{
    private static final String SQLlista="Select * from tipoComprobante";
    @Override
    public List<TipoComprobante> listaTipoComprobante() {
        List<TipoComprobante> listaTipoComprobante=new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLlista);
            ResultSet res=ps.executeQuery();  
            while (res.next()) {
                TipoComprobante tc = new TipoComprobante(
                res.getLong("idTipoComprobante"),
                res.getString("nombreTipo"),
                res.getString("descripcion"));
                listaTipoComprobante.add(tc);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoComprobante;
    }

}
