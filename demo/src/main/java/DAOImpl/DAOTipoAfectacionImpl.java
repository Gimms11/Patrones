package DAOImpl;

import DTO.AfectacionProductos;
import DAO.ConexionBD;
import DAO.DAOTipoAfectacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOTipoAfectacionImpl implements DAOTipoAfectacion {

    @Override
    public List<AfectacionProductos> obtenerTodas() {
        List<AfectacionProductos> lista = new ArrayList<>();
        String sql = "SELECT idafectacion, nombre, descripcion FROM tipoafectacion";

        try (Connection conexion = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                AfectacionProductos af = new AfectacionProductos();
                af.setIdAfectacion(rs.getLong("idafectacion"));
                af.setNombreAfectacion(rs.getString("nombre"));
                af.setDescripcion(rs.getString("descripcion"));
                lista.add(af);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener las afectaciones: " + e.getMessage());
        }

        return lista;
    }
}
