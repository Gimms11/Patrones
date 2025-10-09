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

    private static final String SQLregistrar =
        "INSERT INTO comprobante (fechaemision, numserie, totalfinal, direccionenvio, devengado, idmediopago, idcliente, idusuario, idtipocomprobante) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQLeliminar="Delete from Comprobante where idComprobante = ?";
    private static final String SQLbuscar="Select * from Comprobante where idComprobante = ?";
    private static final String SQLlista="Select * from Comprobante";

    @Override
    public void registarComprobante(Comprobante comp) {
        try (Connection conn = ConexionBD.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(SQLregistrar)) {

            ps.setDate(1, java.sql.Date.valueOf(comp.getFechaEmision())); // 👈 importante
            ps.setString(2, comp.getSerie());
            ps.setBigDecimal(3, comp.getTotalFinal());
            ps.setString(4, comp.getDireccionEnvio());
            ps.setBigDecimal(5, comp.getDevengado());
            ps.setLong(6, comp.getIdMedioPago());
            ps.setLong(7, comp.getIdCliente());
            ps.setLong(8, comp.getIdUsuario());
            ps.setLong(9, comp.getIdTipoComprobante());

            ps.executeUpdate();

        } catch (SQLException e) {
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
        List<Comprobante> listaComprobante = new ArrayList<>();
        try (Connection conn = ConexionBD.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SQLlista);
             ResultSet res = ps.executeQuery()) {            
            while (res.next()) {
                Comprobante co = new Comprobante();
                co.setIdComprobante(res.getLong("idComprobante"));
                co.setFechaEmision(res.getDate("FechaEmision").toLocalDate());
                co.setSerie(res.getString("numSerie"));
                co.setTotalFinal(res.getBigDecimal("TotalFinal"));
                co.setDireccionEnvio(res.getString("direccionEnvio"));
                co.setDevengado(res.getBigDecimal("devengado"));
                co.setIdMedioPago(res.getLong("idMedioPago"));
                co.setIdCliente(res.getLong("idCliente"));
                co.setIdUsuario(res.getLong("idUsuario"));
                co.setIdTipoComprobante(res.getLong("idTipoComprobante"));
                listaComprobante.add(co);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar comprobantes: " + e.getMessage(), e);
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
