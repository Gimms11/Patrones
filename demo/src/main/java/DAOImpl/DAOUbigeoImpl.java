package DAOImpl;

import DAO.ConexionBD;
import DAO.DAOUbigeo;
import DTO.Departamento;
import DTO.Provincia;
import DTO.Distrito;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOUbigeoImpl implements DAOUbigeo {

    @Override
    public List<Departamento> obtenerTodosDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT iddepartamento, nombre FROM departamento ORDER BY nombre";

        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Departamento d = new Departamento();
                d.setIdDepartamento(rs.getLong("iddepartamento"));
                d.setNombreDepartamento(rs.getString("nombre"));
                departamentos.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar departamentos", e);
        }
        return departamentos;
    }

    @Override
    public List<Provincia> obtenerProvinciasPorDepartamento(Long idDepartamento) {
        List<Provincia> provincias = new ArrayList<>();
        String sql = "SELECT idprovincia, nombre FROM provincia WHERE iddepartamento = ? ORDER BY nombre";

        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idDepartamento);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Provincia p = new Provincia();
                    p.setIdProvincia(rs.getLong("idprovincia"));
                    p.setNombreProvincia(rs.getString("nombre"));
                    p.setIdDepartamento(idDepartamento);
                    provincias.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar provincias", e);
        }
        return provincias;
    }

    @Override
    public List<Distrito> obtenerDistritosPorProvincia(Long idProvincia) {
        List<Distrito> distritos = new ArrayList<>();
        String sql = "SELECT iddistrito, nombre FROM distrito WHERE idprovincia = ? ORDER BY nombre";

        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idProvincia);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Distrito d = new Distrito();
                    d.setIdDistrito(rs.getLong("iddistrito"));
                    d.setNombreDistrito(rs.getString("nombre"));
                    d.setIdProvincia(idProvincia);
                    distritos.add(d);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar distritos", e);
        }
        return distritos;
    }
}
