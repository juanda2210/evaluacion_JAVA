package com.apex.dao;

import com.apex.database.ConexionDB;
import com.apex.models.Cliente;
import com.apex.models.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private static final ClienteDAO clienteDAO = new ClienteDAO();

    public static ClienteDAO instanciaUnica() {
        return clienteDAO;
    }

    public void crear(Cliente cliente) throws SQLException {

        String sql = """
                INSERT INTO clientes (nombre, documento, correo, telefono, situacion_crediticia)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConexionDB.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDocumento());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getSituacionCrediticia());

            ps.executeUpdate();
        }
    }


    public Cliente consultarClientePorId(int idCliente) throws SQLException {

        String sql = """
                SELECT id, nombre, documento, correo, telefono, situacion_crediticia
                FROM clientes
                WHERE id = ?
                """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Cliente cliente = new Cliente();

                    cliente.setId(rs.getInt("id"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setDocumento(rs.getString("documento"));
                    cliente.setCorreo(rs.getString("correo"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setSituacionCrediticia(rs.getString("situacion_crediticia"));

                    return cliente;
                }
            }
        }

        return null;
    }


    public List<Cliente> listar() throws SQLException {

        String sql = """
                SELECT id, nombre, documento, correo, telefono
                FROM clientes
                """;

        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDocumento(rs.getString("documento"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setTelefono(rs.getString("telefono"));

                clientes.add(cliente);
            }
        }
        return clientes;
    }


    private Cliente consultarClientePorNombre(String nombre) throws SQLException {
        List<Cliente> clientes = this.listar();

        return clientes.stream()
                .filter(cliente -> cliente.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    public int consultarIdCliente (String documento) throws SQLException {
        Cliente clienteEncontrado = this.clientePorDocumento(documento);
        if (clienteEncontrado != null) {
            return clienteEncontrado.getId();
        } else {
            throw new IllegalArgumentException("Cliente no encontrado en la base de datos");
        }
    }


    // Esta es una buena solucion, e incluso bastante logica pero no es lo más escalable profesionalmente hablando
    //Traer muchos datos puede causar un problema en nuestro codigo
    /*public void consultarPrestamo (String nombre) throws SQLException {

        int id = this.consultarIdCliente(nombre);

        PrestamoDAO prestamoDAO = new PrestamoDAO();
        List<Prestamo> prestamos = prestamoDAO.listar();

        Prestamo prestamoSeleccionado = prestamos.stream()
                .filter(prestamo -> prestamo.getCliente_id() == id)
                .findFirst()
                .orElse(null);

        if (prestamoSeleccionado != null) {
            prestamoSeleccionado.mostrarPrestamo();
        } else {
            throw new SQLException("No fue encontrado algun prestamo a nombre del cliente seleccionado");
        }
    }*/

    public Prestamo buscarPorCliente(int clienteId) throws SQLException {

        String sql = """
            SELECT id, cliente_id, empleado_id, monto, interes,
                   cuotas, fecha_inicio, estado
            FROM prestamos
            WHERE cliente_id = ?
            """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clienteId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Prestamo prestamo = new Prestamo();

                    prestamo.setId(rs.getInt("id"));
                    prestamo.setCliente_id(rs.getInt("cliente_id"));
                    prestamo.setEmpleado_id(rs.getInt("empleado_id"));
                    prestamo.setMonto(rs.getDouble("monto"));
                    prestamo.setInteres(rs.getDouble("interes"));
                    prestamo.setCuotas(rs.getInt("cuotas"));
                    prestamo.setFecha_inicio(rs.getDate("fecha_inicio").toLocalDate());
                    prestamo.setEstado(rs.getString("estado"));

                    return prestamo;
                }
            }
        }

        return null;
    }

    public void actualizarSituacionFinanciera(int idCliente, String situacionCrediticia) throws SQLException {
        String sql = """
            UPDATE clientes
            SET situacion_crediticia = ?
            WHERE id = ?
            """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, situacionCrediticia);
            ps.setInt(2, idCliente);

            ps.executeUpdate();
        }
    }

    public List<Cliente> clientesMorosos() throws SQLException {

        String sql = """
            SELECT id, nombre, documento, correo, telefono, situacion_crediticia
            FROM clientes
            WHERE situacion_crediticia = ?
            """;

        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "Moroso");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Cliente cliente = new Cliente();

                    cliente.setId(rs.getInt("id"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setDocumento(rs.getString("documento"));
                    cliente.setCorreo(rs.getString("correo"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setSituacionCrediticia(rs.getString("situacion_crediticia"));

                    clientes.add(cliente);
                }
            }
        }

        return clientes;
    }


    public Cliente clientePorDocumento(String documento) throws SQLException {
        List<Cliente> clientes = this.listar();

        return clientes.stream()
                .filter(cliente -> cliente.getDocumento().equalsIgnoreCase(documento))
                .findFirst()
                .orElse(null);
    }
}
