package repository;

import DAO.ConexionBD;
import DTO.TipoDocumento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoRepositoryImpl implements TipoDocumentoRepository {

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
