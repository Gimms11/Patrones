package DAOImpl;

import DTO.CategoriaProductos;
import DAO.ConexionBD;
import DAO.DAOCategoriaProducto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOCategoriaProductoImpl implements DAOCategoriaProducto {

    @Override
    public List<CategoriaProductos> obtenerTodas() {
        List<CategoriaProductos> lista = new ArrayList<>();
        String sql = "SELECT idcategoria, nombre, descripcion FROM tipocategoria";

        try (Connection conexion = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoriaProductos cat = new CategoriaProductos();
                cat.setIdCategoria(rs.getLong("idcategoria"));
                cat.setNombreCategoria(rs.getString("nombre"));
                cat.setDescripcion(rs.getString("descripcion"));
                lista.add(cat);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al obtener las categorías: " + e.getMessage());
        }

        return lista;
    }
}
