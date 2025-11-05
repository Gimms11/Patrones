package DAOImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.ConexionBD;
import DAO.DAOComprobante;
import DTO.Comprobante;


public class DAOComprobanteImpl implements DAOComprobante{

    private Comprobante mapearComprobante(ResultSet res) throws SQLException {
        Comprobante co = new Comprobante();
        co.setIdComprobante(res.getLong("idcomprobante"));
        co.setFechaEmision(res.getDate("fechaemision").toLocalDate());
        co.setSerie(res.getString("numserie"));
        co.setTotalFinal(res.getBigDecimal("totalfinal"));
        co.setDireccionEnvio(res.getString("direccionenvio"));
        co.setDevengado(res.getBigDecimal("devengado"));
        co.setIdMedioPago(res.getLong("idmediopago"));
        co.setIdCliente(res.getLong("idcliente"));
        co.setIdUsuario(res.getLong("idusuario"));
        co.setIdTipoComprobante(res.getLong("idtipocomprobante"));
        co.setNombreCliente(res.getString("nombrecliente"));
        co.setClienteDistrito(res.getString("nombredistrito"));
        co.setMedioPago(res.getString("mediopago"));
        co.setClienteDocumento(res.getString("clientedocumento"));
        return co;
    }

    private static final String SQLregistrar =
        "INSERT INTO comprobante (fechaemision, numserie, totalfinal, direccionenvio, devengado, idmediopago, idcliente, idusuario, idtipocomprobante) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQLeliminar="Delete from Comprobante where idComprobante = ?";
    private static final String SQLbuscar="Select * from Comprobante where idComprobante = ?";

    private static final String SQLlista =
        "SELECT c.*, cl.nombres AS nombrecliente, d.nombre AS nombredistrito, " +
        "mp.nombre AS mediopago, cl.numdocumento AS clientedocumento " +
        "FROM comprobante c " +
        "JOIN cliente cl ON c.idcliente = cl.idcliente " +
        "JOIN distrito d ON cl.iddistrito = d.iddistrito " +
        "JOIN mediopago mp ON c.idmediopago = mp.idmediopago";

    private static final String SQLfiltroHoy =
        "SELECT c.*, cl.nombres AS nombrecliente, d.nombre AS nombredistrito, " +
        "mp.nombre AS mediopago, cl.numdocumento AS clientedocumento " +
        "FROM comprobante c " +
        "JOIN cliente cl ON c.idcliente = cl.idcliente " +
        "JOIN distrito d ON cl.iddistrito = d.iddistrito " +
        "JOIN mediopago mp ON c.idmediopago = mp.idmediopago " +
        "WHERE DATE(c.fechaemision) = CURRENT_DATE";

    private static final String SQLfiltroSemana =
        "SELECT c.*, cl.nombres AS nombrecliente, d.nombre AS nombredistrito, " +
        "mp.nombre AS mediopago, cl.numdocumento AS clientedocumento " +
        "FROM comprobante c " +
        "JOIN cliente cl ON c.idcliente = cl.idcliente " +
        "JOIN distrito d ON cl.iddistrito = d.iddistrito " +
        "JOIN mediopago mp ON c.idmediopago = mp.idmediopago " +
        "WHERE c.fechaemision >= (CURRENT_DATE - INTERVAL '7 days') " +
        "AND c.fechaemision <= CURRENT_DATE";

    private static final String SQLfiltroMesInterval =
        "SELECT c.*, cl.nombres AS nombrecliente, d.nombre AS nombredistrito, " +
        "mp.nombre AS mediopago, cl.numdocumento AS clientedocumento " +
        "FROM comprobante c " +
        "JOIN cliente cl ON c.idcliente = cl.idcliente " +
        "JOIN distrito d ON cl.iddistrito = d.iddistrito " +
        "JOIN mediopago mp ON c.idmediopago = mp.idmediopago " +
        "WHERE c.fechaemision >= (CURRENT_DATE - INTERVAL '1 month') " +
        "AND c.fechaemision <= CURRENT_DATE";

    private static final String SQLfiltroAño =
        "SELECT c.*, cl.nombres AS nombrecliente, d.nombre AS nombredistrito, " +
        "mp.nombre AS mediopago, cl.numdocumento AS clientedocumento " +
        "FROM comprobante c " +
        "JOIN cliente cl ON c.idcliente = cl.idcliente " +
        "JOIN distrito d ON cl.iddistrito = d.iddistrito " +
        "JOIN mediopago mp ON c.idmediopago = mp.idmediopago " +
        "WHERE c.fechaemision >= (CURRENT_DATE - INTERVAL '1 year') " +
        "AND c.fechaemision <= CURRENT_DATE";

    private static final String SQLfiltroPorDNI =
        "SELECT c.*, cl.nombres AS nombrecliente, d.nombre AS nombredistrito, " +
        "mp.nombre AS mediopago, cl.numdocumento AS clientedocumento " +
        "FROM comprobante c " +
        "JOIN cliente cl ON c.idcliente = cl.idcliente " +
        "JOIN distrito d ON cl.iddistrito = d.iddistrito " +
        "JOIN mediopago mp ON c.idmediopago = mp.idmediopago " +
        "WHERE cl.numdocumento = ?";


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
                listaComprobante.add(mapearComprobante(res));
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

    public List<Comprobante> filtrarComprovanteHoy (){
        List<Comprobante> comprobanteHoy= new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLfiltroHoy);
            ResultSet res=ps.executeQuery();
            while (res.next()) {
                comprobanteHoy.add(mapearComprobante(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar comprobantes: " + e.getMessage(), e);
        }        return comprobanteHoy ;
    }

    public List<Comprobante> filtrarComprobantesSemana(){
        List<Comprobante> comprobanteSemana= new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLfiltroSemana);
            ResultSet res=ps.executeQuery();
            while (res.next()) {
                comprobanteSemana.add(mapearComprobante(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar comprobantes: " + e.getMessage(), e);
        }   
        return comprobanteSemana;
    }

    public List<Comprobante> filtrarComprobantePorMes(){
        List<Comprobante> comprobanteMes= new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLfiltroMesInterval);
            ResultSet res=ps.executeQuery();
            while (res.next()) {
                comprobanteMes.add(mapearComprobante(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar comprobantes: " + e.getMessage(), e);
        } 
        return comprobanteMes;
    }
    public List<Comprobante> filtrarComprobantePorAño(){
        List<Comprobante> comprobanteAño= new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLfiltroAño);
            ResultSet res=ps.executeQuery();
            while (res.next()) {
                comprobanteAño.add(mapearComprobante(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar comprobantes: " + e.getMessage(), e);
        } 
        return comprobanteAño;
    }
    public List<Comprobante> filtrarComprobantePorCliente(Long numDocumento){
        List<Comprobante> comprobanteCliente= new ArrayList<>();
        try {
            Connection conn=ConexionBD.getInstance().getConnection();
            PreparedStatement ps= conn.prepareStatement(SQLfiltroPorDNI);
            ps.setLong(1, numDocumento);
            ResultSet res=ps.executeQuery();
            while (res.next()) {
                comprobanteCliente.add(mapearComprobante(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar comprobantes: " + e.getMessage(), e);
        }
        return comprobanteCliente;
    }
}
