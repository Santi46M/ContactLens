package repository;

import db.DatabaseConfig;
import model.HistorialLente;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialRepository {

    public void guardarCalculo(String nombreLente, double curvaBase, String material, double indice,
                               double esferico, double cilindro, double curva1, double curva2, double torsion) {
        String sql = """
            INSERT INTO historial_lentes
            (nombre_lente, curva_base, material, indice, esferico, cilindro, curva1, curva2, torsion)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (
                Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, nombreLente);
            ps.setDouble(2, curvaBase);
            ps.setString(3, material);
            ps.setDouble(4, indice);
            ps.setDouble(5, esferico);
            ps.setDouble(6, cilindro);
            ps.setDouble(7, curva1);
            ps.setDouble(8, curva2);
            ps.setDouble(9, torsion);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog((Component) null, "No se pudo guardar el cálculo.");
        }
    }

    public List<HistorialLente> obtenerHistorial() {
        List<HistorialLente> lista = new ArrayList<>();

        String sql = """
            SELECT nombre_lente, fecha, curva_base, material, indice, esferico, cilindro, curva1, curva2, torsion
            FROM historial_lentes
            ORDER BY id DESC
            """;

        try (
                Connection conn = DatabaseConfig.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                lista.add(new HistorialLente(
                        rs.getTimestamp("fecha"),
                        rs.getString("nombre_lente"),
                        rs.getDouble("curva_base"),
                        rs.getString("material"),
                        rs.getDouble("indice"),
                        rs.getDouble("esferico"),
                        rs.getDouble("cilindro"),
                        rs.getDouble("curva1"),
                        rs.getDouble("curva2"),
                        rs.getDouble("torsion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog((Component) null, "No se pudo cargar el historial.");
        }

        return lista;
    }
}