package com.apex.dao;

import com.apex.database.ConexionDB;
import com.apex.models.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private static final EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public static EmpleadoDAO instanciaUnica() {
        return empleadoDAO;
    }

    public void crear(Empleado empleado) throws SQLException {

        String sql = """
        INSERT INTO empleados (nombre, documento, rol, correo, salario)
        VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDocumento());
            ps.setString(3, empleado.getRol());
            ps.setString(4, empleado.getCorreo());
            ps.setDouble(5, empleado.getSalario());

            ps.executeUpdate();
        }
    }

    public Empleado buscarPorId(int id) throws  SQLException {

        String sql = """
        SELECT id, nombre, documento, rol, correo, salario
        FROM empleados
        WHERE id = ?
        """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Empleado empleado = new Empleado();

                    empleado.setId(rs.getInt("id"));
                    empleado.setNombre(rs.getString("nombre"));
                    empleado.setDocumento(rs.getString("documento"));
                    empleado.setRol(rs.getString("rol"));
                    empleado.setCorreo(rs.getString("correo"));
                    empleado.setSalario(rs.getDouble("salario"));
                    return empleado;
                }
            }
        }

        return null;
    }

    public List<Empleado> listar() throws SQLException {

        String sql = """
                SELECT id, nombre, documento, rol, correo, salario
                FROM empleados
                """;

        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Empleado empleado = new Empleado();

                empleado.setId(rs.getInt("id"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setDocumento(rs.getString("documento"));
                empleado.setRol(rs.getString("rol"));
                empleado.setCorreo(rs.getString("correo"));
                empleado.setSalario(rs.getDouble("salario"));

                empleados.add(empleado);
            }
        }
        return empleados;
    }

    private Empleado consultarPorNombre(String nombre) throws SQLException {
        List<Empleado> empleados = this.listar();

        return empleados.stream()
                .filter(empleado -> empleado.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);

    }

    public int consultarIdEmpleado(String documento) throws SQLException {
        Empleado empleadoEncontrado = this.empleadoPorDocumento(documento);

        if (empleadoEncontrado != null) {
            return empleadoEncontrado.getId();
        } else {
            throw new IllegalArgumentException("Empleado no encontrado en la base de datos");
        }
    }

    public Empleado empleadoPorDocumento(String documento) throws SQLException {
        List<Empleado> empleados = this.listar();

        return empleados.stream()
                .filter(empleado -> empleado.getDocumento().equalsIgnoreCase(documento))
                .findFirst()
                .orElse(null);
    }
}
