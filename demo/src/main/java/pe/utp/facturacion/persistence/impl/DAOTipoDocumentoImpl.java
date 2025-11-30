package pe.utp.facturacion.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.utp.facturacion.persistence.ConexionBD;
import pe.utp.facturacion.persistence.dao.DAOTipoDocumento;
import pe.utp.facturacion.model.TipoDocumento;

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

    @Override
    public TipoDocumento obtener(Long id) {
        String sql = "SELECT iddocumento, nombre, descripcion FROM tipodocumento WHERE iddocumento = ?";

        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TipoDocumento td = new TipoDocumento();
                    td.setIdDocumento(rs.getLong("iddocumento"));
                    td.setNombreDocumento(rs.getString("nombre"));
                    td.setDescripcion(rs.getString("descripcion"));
                    return td;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener tipo de documento", e);
        }
        return null;
    }
}