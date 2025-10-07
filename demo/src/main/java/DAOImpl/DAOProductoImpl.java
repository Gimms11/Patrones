package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOProducto;
import DTO.Producto;

public class DAOProductoImpl implements DAOProducto{
    
    private static final String SQLregistrar="Insert into producto (nombre, precio, stock, descripcion, unidadMedida, idAfectacion, idCategoria) " +
                                            "values (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQLeliminar="delete from producto where idProducto = ?";
    private static final String SQLactualizar="Update producto set nombre = ?, precio = ?, stock = ?, descripcion = ?, unidadMedida = ?, idAfectacion = ?, idCategoria = ? " +
                                                "where idProducto = ?";
    private static final String SQLbuscar="Select * from producto where idProducto = ?";
    private static final String SQLlista="Select * from producto";

    @Override
    public void registarProducto(Producto produc) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLregistrar);
            ps.setString(1, produc.getNombre());
            ps.setBigDecimal(2, produc.getPrecio());
            ps.setInt(3, produc.getStock());
            ps.setString(4, produc.getDescripcion());
            ps.setString(5, produc.getUnidadMedida());
            ps.setLong(6, produc.getIdTipoAfectacion());
            ps.setLong(7, produc.getIdCategoria());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarProducto(Producto produc) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLactualizar);
            ps.setString(1, produc.getNombre());
            ps.setBigDecimal(2, produc.getPrecio());
            ps.setInt(3, produc.getStock());
            ps.setString(4, produc.getDescripcion());
            ps.setString(5, produc.getUnidadMedida());
            ps.setLong(6, produc.getIdTipoAfectacion());
            ps.setLong(7, produc.getIdCategoria());
            ps.setLong(8,produc.getIdProducto());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarProducto(Producto produc) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLeliminar);
            ps.setLong(1, produc.getIdProducto());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Producto> listarProducto() {
        List<Producto> listaProducto=new ArrayList<>();
        try{     
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLlista);
            ResultSet res=ps.executeQuery();            
            while (res.next()) {
                Producto p = new Producto(
                res.getLong("idProducto"),
                res.getString("nombre"),
                res.getBigDecimal("precio"),
                res.getInt("stock"),
                res.getString("descripcion"),
                res.getString("unidadMedida"),
                res.getLong("idAfectacion"),
                res.getLong("idCategoria"));
                listaProducto.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listaProducto;
    }

    @Override
    public Producto buscarProducto(Producto produc) {
        Producto p=null;
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLbuscar);
            ps.setLong(1, produc.getIdProducto());;
            ResultSet res=ps.executeQuery();
            if (res.next()) {
                p = new Producto(
                res.getLong("idProducto"),
                res.getString("nombre"),
                res.getBigDecimal("precio"),
                res.getInt("stock"),
                res.getString("descripcion"),
                res.getString("unidadMedida"),
                res.getLong("idAfectacion"),
                res.getLong("idCategoria"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return p;
    }

}
