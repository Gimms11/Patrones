package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOMedioPago;
import DTO.MedioPago;

public class DAOMedioPagoImpl implements DAOMedioPago{

    @Override
    public List<MedioPago> obtenerTodos() {
        List<MedioPago> medioPagoList = new ArrayList<>();
        String sql = "SELECT idmediopago, nombre, descripcion FROM mediopago ORDER BY nombre";

        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MedioPago medioPago = new MedioPago();
                medioPago.setIdMedioPago(rs.getLong("idmediopago"));
                medioPago.setNombreMedioPago(rs.getString("nombre"));
                medioPago.setDescripcion(rs.getString("descripcion"));
                medioPagoList.add(medioPago);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar los medios de Pagos", e);
        }

        return medioPagoList;
    }
}
