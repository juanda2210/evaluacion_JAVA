package com.apex.views;

import com.apex.dao.ClienteDAO;
import com.apex.dao.EmpleadoDAO;
import com.apex.dao.PrestamoDAO;
import com.apex.models.Prestamo;
import com.apex.models.Simulacion;
import com.apex.util.FileUtils;
import com.apex.util.ScannerUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PrestamosView {

    public static final int CREAR_PRESTAMOS = 1;
    public static final int SIMULACION_PRESTAMO = 2;
    public static final int CAMBIAR_ESTADO = 3;
    public static final int EXPORTAR = 4;
    public static final int SALIR = 5;

    public static PrestamoDAO prestamoDAO = PrestamoDAO.instanciaUnica();
    public static ClienteDAO clienteDAO = ClienteDAO.instanciaUnica();
    public static EmpleadoDAO empleadoDAO = EmpleadoDAO.instanciaUnica();

    public static void menu() {
        while (true) {

            int option = ScannerUtils.capturarNumero("""
            \n===== MENÚ DE PRÉSTAMOS =====
            1. Crear préstamo
            2. Simulación de préstamo
            3. Cambiar estado a Pagado
            4. Exportar prestamo a archivo
            5. Volver al menu principal""");

            switch (option) {

                case CREAR_PRESTAMOS -> {
                    // Lógica para crear préstamos

                    try {
                        String documentoCliente = ScannerUtils.capturarTexto("Documento del cliente");
                        int idCliente = clienteDAO.consultarIdCliente(documentoCliente);

                        String documentoEmpleado = ScannerUtils.capturarTexto("Documento del empleado");
                        int idEmpleado = empleadoDAO.consultarIdEmpleado(documentoEmpleado);

                        double monto = ScannerUtils.capturarDecimal("Monto total");
                        int periodoDeTiempo = ScannerUtils.capturarNumero("Periodo (meses)");
                        Simulacion simulacion = new Simulacion(monto, periodoDeTiempo);
                        double interes = simulacion.getInteresAnual();


                        Prestamo prestamo = new Prestamo(idCliente, idEmpleado, monto, interes, periodoDeTiempo, LocalDate.now());
                        prestamoDAO.crear(prestamo);

                        System.out.println("Su credito ha sido generado de la siguiente manera: ");
                        simulacion.mostrarSimulacion();
                    } catch (SQLException e) {
                        System.err.println("Su prestamo no se ha podido generar");
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                    }
                }

                case SIMULACION_PRESTAMO -> {
                    // Lógica para simular préstamo

                    Prestamo prestamo = new Prestamo();
                    prestamo.calucularSimulacion();
                }

                case CAMBIAR_ESTADO -> {
                    // Lógica para cambiar estado

                    try {
                        String documento = ScannerUtils.capturarTexto("Documento del cliente");
                        int clienteId = clienteDAO.consultarIdCliente(documento);

                        List<Prestamo> prestamosDelCliente = prestamoDAO.prestamosPorCliente(clienteId);
                        prestamosDelCliente.forEach(prestamo -> {
                            try {
                                prestamo.mostrarPrestamoConIdCliente();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        int prestamoId = ScannerUtils.capturarNumero("\nId del prestamo");
                        Prestamo prestamo = prestamoDAO.seleccionarPorId(prestamoId);
                        prestamo.cambiarEstado();
                        prestamoDAO.actualizarEstado(prestamoId, "Pagado");

                        System.out.println("Estado del prestamo actualizado satisfactoriamente");
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }


                }

                case EXPORTAR -> {
                    try {
                        String documentoDelCliente = ScannerUtils.capturarTexto("Documento del cliente");
                        int clienteId = clienteDAO.consultarIdCliente(documentoDelCliente);

                        List<Prestamo> prestamosDelCliente = prestamoDAO.prestamosPorCliente(clienteId);

                        prestamosDelCliente.forEach(Prestamo::mostrarPrestamo);

                        int id = ScannerUtils.capturarNumero("Id del prestamo");
                        Prestamo prestamo = prestamoDAO.seleccionarPorId(id);

                        if (prestamo != null) {
                            FileUtils.escribirPrestamo(prestamo);
                            System.out.println("\nPrestamo exportado a archivo satisfactoriamente");
                        } else {
                            System.err.println("Prestamo no encontrado por id " + id);
                        }
                    } catch (SQLException e) {
                        System.err.println("Falla en la base de datos al exportar prestamo");
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
