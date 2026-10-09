package com.apex.dao;

import com.apex.database.ConexionDB;
import com.apex.models.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    private static final PrestamoDAO prestamoDAO = new PrestamoDAO();

    public static PrestamoDAO instanciaUnica() {
        return prestamoDAO;
    }

    public List<Prestamo> listar() throws SQLException {

        String sql = """
                SELECT id, cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado
                FROM prestamos
                """;

        List<Prestamo> prestamos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Prestamo prestamo = new Prestamo();

                prestamo.setId(rs.getInt("id"));
                prestamo.setCliente_id(rs.getInt("cliente_id"));
                prestamo.setEmpleado_id(rs.getInt("empleado_id"));
                prestamo.setMonto(rs.getDouble("monto"));
                prestamo.setInteres(rs.getDouble("interes"));
                prestamo.setCuotas(rs.getInt("cuotas"));
                prestamo.setFecha_inicio(rs.getDate("fecha_inicio").toLocalDate());
                prestamo.setEstado(rs.getString("estado"));

                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    public void crear(Prestamo prestamo) throws SQLException {
        String sql = """
                INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConexionDB.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, prestamo.getCliente_id());
            ps.setInt(2, prestamo.getEmpleado_id());
            ps.setDouble(3, prestamo.getMonto());
            ps.setDouble(4, prestamo.getInteres());
            ps.setInt(5, prestamo.getCuotas());
            ps.setDate(6, Date.valueOf(prestamo.getFecha_inicio()));
            ps.setString(7, prestamo.getEstado());

            ps.executeUpdate();
        }
    }

    public List<Prestamo> prestamosPorCliente(int clienteId) throws SQLException {
        List<Prestamo> prestamos = this.listar();

        List<Prestamo> prestamosdelCliente = prestamos.stream()
                .filter(prestamo -> prestamo.getCliente_id() == clienteId)
                .toList();

        if (prestamosdelCliente.isEmpty()) {
            throw new SQLException("No se encontraron prestamos para el cliente con clienteId " + clienteId);
        } else {
            return prestamosdelCliente;
        }
    }

    public Prestamo seleccionarPorId (int id) throws SQLException {
        List<Prestamo> prestamos = this.listar();

        Prestamo prestamoPorBuscar = prestamos.stream()
                .filter(prestamo -> prestamo.getId() == id)
                .findFirst()
                .orElse(null);

        if (prestamoPorBuscar != null) {
            return prestamoPorBuscar;
        }

        throw new SQLException("El prestamo del id " + id + " no fue encontrado en base de datos");
    }

    public void actualizarEstado(int prestamoId, String estado) throws SQLException {

        String sql = """
            UPDATE prestamos
            SET estado = ?
            WHERE id = ?
            """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, prestamoId);

            ps.executeUpdate();
        }
    }

    public List<Prestamo> prestamosPendientes() throws SQLException {

        String sql = """
            SELECT id, cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado
            FROM prestamos
            WHERE estado = ?
            """;

        List<Prestamo> prestamos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Pendiente");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Prestamo prestamo = new Prestamo();

                    prestamo.setId(rs.getInt("id"));
                    prestamo.setCliente_id(rs.getInt("cliente_id"));
                    prestamo.setEmpleado_id(rs.getInt("empleado_id"));
                    prestamo.setMonto(rs.getDouble("monto"));
                    prestamo.setInteres(rs.getDouble("interes"));
                    prestamo.setCuotas(rs.getInt("cuotas"));
                    prestamo.setFecha_inicio(rs.getDate("fecha_inicio").toLocalDate());
                    prestamo.setEstado(rs.getString("estado"));

                    prestamos.add(prestamo);
                }
            }
        }

        return prestamos;
    }

    public List<Prestamo> prestamosVencidos() throws SQLException {

        String sql = """
            SELECT id, cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado
            FROM prestamos
            WHERE estado = ?
            """;

        List<Prestamo> prestamos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Pagado");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Prestamo prestamo = new Prestamo();

                    prestamo.setId(rs.getInt("id"));
                    prestamo.setCliente_id(rs.getInt("cliente_id"));
                    prestamo.setEmpleado_id(rs.getInt("empleado_id"));
                    prestamo.setMonto(rs.getDouble("monto"));
                    prestamo.setInteres(rs.getDouble("interes"));
                    prestamo.setCuotas(rs.getInt("cuotas"));
                    prestamo.setFecha_inicio(rs.getDate("fecha_inicio").toLocalDate());
                    prestamo.setEstado(rs.getString("estado"));

                    prestamos.add(prestamo);
                }
            }
        }

        return prestamos;
    }


}
