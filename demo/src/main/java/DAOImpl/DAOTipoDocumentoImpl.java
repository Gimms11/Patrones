package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOTipoDocumento;
import DTO.TipoDocumento;

public class DAOTipoDocumentoImpl implements DAOTipoDocumento{

    @Override
    public List<TipoDocumento> obtenerTodos() {
        List<TipoDocumento> documentos = new ArrayList<>();
        String sql = "SELECT iddocumento, nombre, descripcion FROM tipodocumento ORDER BY nombre";

        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TipoDocumento td = new TipoDocumento();
                td.setIdDocumento(rs.getLong("iddocumento"));
                td.setNombreDocumento(rs.getString("nombre"));
                td.setDescripcion(rs.getString("descripcion"));
                documentos.add(td);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar los tipos de documento", e);
        }

        return documentos;
    }
}