package com.apex.dao;

import com.apex.database.ConexionDB;
import com.apex.models.Cliente;
import com.apex.models.Pago;
import com.apex.models.Prestamo;
import com.apex.util.ScannerUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagosDAO {

    private static final PagosDAO pagosDAO = new PagosDAO();
    private static final ClienteDAO clienteDAO = ClienteDAO.instanciaUnica();


    public static PagosDAO instanciaUnica() {
        return pagosDAO;
    }


    PrestamoDAO prestamoDAO = PrestamoDAO.instanciaUnica();

    public void registrarAbono(Pago pago) throws SQLException {
        String sql = """
                INSERT INTO pagos (prestamo_id, fecha_pago, monto)
                VALUES(?, ?, ?)
                """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, pago.getPrestamo_id());
            ps.setDate(2, Date.valueOf(pago.getFecha_pago()));
            ps.setDouble(3, pago.getMonto());

            ps.executeUpdate();
        }
    }

    public List<Pago> pagosPorIdPrestamo(int idPrestamo) throws SQLException  {

        List<Pago> pagos = new ArrayList<>();

        String sql = """
            SELECT id, prestamo_id, fecha_pago, monto
            FROM pagos
            WHERE prestamo_id = ?
            """;

        try (PreparedStatement ps = ConexionDB.getConnection().prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Pago pago = new Pago();

                pago.setId(rs.getInt("id"));
                pago.setPrestamo_id(rs.getInt("prestamo_id"));
                pago.setFecha_pago(rs.getDate("fecha_pago").toLocalDate());
                pago.setMonto(rs.getDouble("monto"));

                pagos.add(pago);
            }

        }

        return pagos;
    }

    public void verificarMonto (double monto) throws SQLException {

        String documento = ScannerUtils.capturarTexto("Documento del cliente");
        int idCliente = clienteDAO.consultarIdCliente(documento);

        List<Prestamo> prestamosDelCliente = prestamoDAO.prestamosPorCliente(idCliente);
        prestamosDelCliente.forEach(prestamo -> prestamo.mostrarPrestamo());

        int idPrestamo = ScannerUtils.capturarNumero("Id del prestamo");
        Prestamo prestamo = prestamoDAO.seleccionarPorId(idPrestamo);

        if(prestamo.getEstado().equalsIgnoreCase("Pagado")) {
            throw new IllegalArgumentException("Este prestamo ya fue pagado, por lo tanto, no puede hacer abonos");
        }

        List<Pago> pagosPorIdPrestamo = this.pagosPorIdPrestamo(idPrestamo);

        double sumaDePagos = pagosPorIdPrestamo.stream()
                .mapToDouble(pago -> pago.getMonto())
                .sum();

        double sobrante = prestamo.getMonto() - sumaDePagos;
        double futuroSobrante = sobrante - monto;

        if (futuroSobrante >= 0) {
            LocalDate fechaDePago = LocalDate.now();
            Pago pago = new Pago(idPrestamo, fechaDePago, monto);
            this.registrarAbono(pago);
            System.out.println("Abono hecho satisfactoriamente");

            if (futuroSobrante > 0) {
                System.out.println("Aun queda pendiente por pagar " + futuroSobrante);
            } else {
                System.out.println("Ya fue pagado completamente el prestamo, enhorabuena");
            }
        } else {
            throw new IllegalArgumentException("No puede abonar este monto, ya que estaria pagando más de lo que se debe," +
                    " vuelva a registrar el abono por " + sobrante + " o un abono menor a este");
        }
    }

    public List<Pago> todosLosPagos() throws SQLException {
        String sql = """
                SELECT id, prestamo_id, fecha_pago, monto
                FROM pagos
                """;

        List<Pago> pagos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pago pago = new Pago();

                pago.setId(rs.getInt("id"));
                pago.setPrestamo_id(rs.getInt("prestamo_id"));
                pago.setFecha_pago(rs.getDate("fecha_pago").toLocalDate());
                pago.setMonto(rs.getDouble("monto"));

                pagos.add(pago);
            }
        }
        return pagos;
    }

    public Pago buscarPagoPorId(int id) throws SQLException {
        List<Pago> todosLosPagos = this.todosLosPagos();

        return todosLosPagos.stream()
                .filter(pago -> pago.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
