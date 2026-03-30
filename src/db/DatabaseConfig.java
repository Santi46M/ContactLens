package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:hsqldb:file:data/lentesdb;shutdown=true";
    private static final String DB_USER = "SA";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            System.out.println("Driver HSQLDB cargado correctamente");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de HSQLDB en el classpath.", e);
        }

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}