package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOTipoDocumento;
import DTO.TipoDocumento;

public class DAOTipoDocumentoImpl implements DAOTipoDocumento{
    private static final String SQLlista="Select * from tipoDoumento";
    @Override
    public List<TipoDocumento> listaTipoDocu() {
        List<TipoDocumento> listaTipoDocumento=new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLlista);
            ResultSet res=ps.executeQuery();  
            while (res.next()) {
                TipoDocumento td = new TipoDocumento(
                res.getLong("idDocumento"),
                res.getString("nombre"),
                res.getString("descripcion"));
                listaTipoDocumento.add(td);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTipoDocu();
    }
}
