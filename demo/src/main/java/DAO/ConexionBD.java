package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static ConexionBD instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://ep-lingering-tooth-ace9356b-pooler.sa-east-1.aws.neon.tech/neondb?sslmode=require";
    private static final String USER = "neondb_owner";
    private static final String PASSWORD = "npg_vODo1sA9HjnS";

    private ConexionBD() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de PostgreSQL", e);
        }
    }

    public static ConexionBD getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new ConexionBD();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            instance = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}