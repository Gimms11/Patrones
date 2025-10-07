package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOComprobante;
import DTO.Comprobante;


public class DAOComprobanteImpl implements DAOComprobante{

    private static final String SQLregistrar="Insert into Comprobante (numSerie, totalFinal, direccionEnvio, devengado, idImpuesto, idMedioPago, idCliente,idUsuario) " +
                                                "values (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQLeliminar="Delete from Comprobante where idComprobante = ?";
    private static final String SQLbuscar="Select * from Comprobante where idComprobante = ?";
    private static final String SQLlista="Select * from Comprobante";
    @Override
    public void registarComprobante(Comprobante comp) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLregistrar);
            ps.setString(1, comp.getSerie());
            ps.setBigDecimal(2, comp.getTotalFinal());
            ps.setString(3, comp.getDireccionEnvio());
            ps.setBigDecimal(4, comp.getDevengado());
            ps.setLong(5, comp.getIdTipoComprobante());
            ps.setLong(6, comp.getIdMedioPago());
            ps.setLong(7, comp.getIdCliente());
            ps.setLong(8, comp.getIdUsuario());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarComprobante(Comprobante comp) {
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLeliminar);
            ps.setLong(1, comp.getIdComprobante());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Comprobante> listarComprobante() {
        List<Comprobante> listaComprobante=new ArrayList<>();
        try{     
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLlista);
            ResultSet res=ps.executeQuery();            
            while (res.next()) {
                Comprobante co = new Comprobante(
                res.getLong("idComprobante"),
                res.getDate("FechaEmision").toLocalDate(),
                res.getString("numSerie"),
                res.getBigDecimal("TotalFinal"),
                res.getString("direccionEnvio"),
                res.getBigDecimal("devengado"),
                res.getLong("idImpuesto"),
                res.getLong("idMedioPago"),
                res.getLong("idCliente"),
                res.getLong("idUsuario"));
                listaComprobante.add(co);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listaComprobante;
    }

    @Override
    public Comprobante buscarComprobante(Comprobante comp) {
        Comprobante co=null;
        try{
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLbuscar);
            ps.setLong(1,comp.getIdComprobante());;
            ResultSet res=ps.executeQuery();
            if (res.next()) {
                co = new Comprobante(
                res.getLong("idComprobante"),
                res.getDate("FechaEmision").toLocalDate(),
                res.getString("numSerie"),
                res.getBigDecimal("TotalFinal"),
                res.getString("direccionEnvio"),
                res.getBigDecimal("devengado"),
                res.getLong("idImpuesto"),
                res.getLong("idMedioPago"),
                res.getLong("idCliente"),
                res.getLong("idUsuario"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return co;
    }

}
