package com.apex.views;

import com.apex.dao.ClienteDAO;
import com.apex.dao.PagosDAO;
import com.apex.dao.PrestamoDAO;
import com.apex.models.GestorPagos;
import com.apex.models.Pago;
import com.apex.models.Prestamo;
import com.apex.util.FileUtils;
import com.apex.util.ScannerUtils;

import java.sql.SQLException;
import java.util.List;


public class PagosView {

    public static final int REGISTRAR_ABONOS = 1;
    public static final int MOSTRAR_SALDO_PENDIENTE = 2;
    public static final int MOSTRAR_HISTORICO_PAGOS = 3;
    public static final int EXPORTAR = 4;
    public static final int A_LISTA = 5;
    public static final int LISTAR_PAGOS = 6;
    public static final int PAGOS_MAYORES_A = 7;
    public static final int SALIR = 8;


    public static PagosDAO pagosDAO = PagosDAO.instanciaUnica();
    public static PrestamoDAO prestamoDAO = PrestamoDAO.instanciaUnica();
    public static ClienteDAO clienteDAO = ClienteDAO.instanciaUnica();
    public static GestorPagos gestorPagos = GestorPagos.instanciaUnica();

    public static void menu() {
        while (true) {

            int option = ScannerUtils.capturarNumero("""
            \n===== MENÚ DE PAGOS =====
            1. Registrar abonos
            2. Mostrar saldo pendiente
            3. Mostrar histórico de pagos
            4. Exportar pago a archivo
            5. Añadir pago a lista
            6. Mostrar pagos de la lista
            7. Mostrar pagos mayores a un valor
            8. Volver al menu principal""");

            switch (option) {

                case REGISTRAR_ABONOS -> {

                    try {
                        double monto = ScannerUtils.capturarDecimal("Monto");
                        pagosDAO.verificarMonto(monto);
                    } catch (IllegalArgumentException | SQLException e) {
                        System.err.println(e.getMessage());
                    }

                }

                case MOSTRAR_SALDO_PENDIENTE -> {
                    // Lógica para mostrar saldo pendiente
                    try {
                        String documentoCliente = ScannerUtils.capturarTexto("Documento del cliente");
                        int id = clienteDAO.consultarIdCliente(documentoCliente);

                        List<Prestamo> prestamosDelCliente = prestamoDAO.prestamosPorCliente(id);

                        prestamosDelCliente.forEach(Prestamo::mostrarPrestamo);

                        int idPrestamo = ScannerUtils.capturarNumero("Id del prestamo");
                        List<Pago> pagosPorIdPrestamo = pagosDAO.pagosPorIdPrestamo(idPrestamo);

                        double sumaDePagos = pagosPorIdPrestamo.stream()
                                .mapToDouble(Pago::getMonto)
                                .sum();

                        Prestamo prestamo = prestamoDAO.seleccionarPorId(idPrestamo);

                        double saldoPendiente = prestamo.getMonto() - sumaDePagos;

                        System.out.println("El saldo pendiente es " + saldoPendiente);
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                }

                case MOSTRAR_HISTORICO_PAGOS -> {
                    // Lógica para mostrar histórico de pagos

                    try {
                        String documentoCliente = ScannerUtils.capturarTexto("Documento del cliente");
                        int id = clienteDAO.consultarIdCliente(documentoCliente);

                        List<Prestamo> prestamosDelCliente = prestamoDAO.prestamosPorCliente(id);

                        prestamosDelCliente.forEach(Prestamo::mostrarPrestamo);

                        int idPrestamo = ScannerUtils.capturarNumero("Id del prestamo");
                        List<Pago> pagosPorIdPrestamo = pagosDAO.pagosPorIdPrestamo(idPrestamo);

                        if (pagosPorIdPrestamo.isEmpty()) {
                            System.err.println("Historico de pagos no encontrado del prestamo id " + idPrestamo);
                        } else {
                            pagosPorIdPrestamo.forEach(Pago::mostrarPago);
                        }

                    } catch (SQLException e) {
                        System.err.println("Error consultando base de datos de pagos");
                    }
                }

                case EXPORTAR -> {
                    try {
                        String documentoDelCliente = ScannerUtils.capturarTexto("Documento del cliente");
                        int clienteId = clienteDAO.consultarIdCliente(documentoDelCliente);

                        List<Prestamo> prestamosDelCliente = prestamoDAO.prestamosPorCliente(clienteId);

                        prestamosDelCliente.forEach(Prestamo::mostrarPrestamo);

                        int idPrestamo = ScannerUtils.capturarNumero("Id del prestamo");

                        List<Pago> pagosPorIdPrestamo = pagosDAO.pagosPorIdPrestamo(idPrestamo);
                        pagosPorIdPrestamo.forEach(Pago::mostrarPago);


                        int id = ScannerUtils.capturarNumero("Id del pago");
                        Pago pago = pagosDAO.buscarPagoPorId(id);

                        if (pago != null) {
                            FileUtils.escribirPago(pago);
                            System.out.println("\nPago exportado a archivo satisfactoriamente");
                        } else {
                            System.err.println("Pago no encontrado en base de datos");
                        }
                    } catch (SQLException e) {
                        System.err.println("Falla en la base de datos al exportar pago");
                    }
                }

                case A_LISTA -> {
                    try {
                        System.out.println("---REGISTRO DE PAGOS----");
                        String documentoDelCliente = ScannerUtils.capturarTexto("Documento del cliente");
                        int clienteId = clienteDAO.consultarIdCliente(documentoDelCliente);

                        List<Prestamo> prestamosDelCliente = prestamoDAO.prestamosPorCliente(clienteId);

                        prestamosDelCliente.forEach(Prestamo::mostrarPrestamo);

                        int idPrestamo = ScannerUtils.capturarNumero("Id del prestamo");

                        List<Pago> pagosPorIdPrestamo = pagosDAO.pagosPorIdPrestamo(idPrestamo);
                        pagosPorIdPrestamo.forEach(Pago::mostrarPago);


                        int id = ScannerUtils.capturarNumero("Id del pago");
                        Pago pago = pagosDAO.buscarPagoPorId(id);

                        if (pago != null) {
                            gestorPagos.registrarPago(pago);
                            System.out.println("---PAGO AÑADIDO A LA LISTA SATISFACTORIAMENTE---");
                        } else {
                            System.err.println("Pago no encontrado en la base de datos");
                        }
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                }

                case LISTAR_PAGOS -> {
                    System.out.println("-----LISTADO DE PAGOS-----");

                    gestorPagos.listarPagos();
                }

                case PAGOS_MAYORES_A -> {
                    double valor = ScannerUtils.capturarDecimal("Pagos mayores a");
                    List<Pago> pagosMayoresA = gestorPagos.mostrarPagosMayoresA(valor);

                    if (pagosMayoresA.isEmpty()) {
                        System.err.println("No existen pagos mayores a ese valor");
                    } else {
                        System.out.println("-----PAGOS MAYORES A " + valor + "------");
                        pagosMayoresA.forEach(Pago::mostrarPago);
                    }
                }

                case SALIR -> {
                    return;
                }

                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
