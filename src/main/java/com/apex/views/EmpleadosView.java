package com.apex.views;

import com.apex.dao.EmpleadoDAO;
import com.apex.models.Empleado;
import com.apex.util.FileUtils;
import com.apex.util.ScannerUtils;

import java.sql.SQLException;

public class EmpleadosView {

    public static final int REGISTRAR_EMPLEADOS = 1;
    public static final int CONSULTAR_EMPLEADOS = 2;
    public static final int EXPORTAR = 3;
    public static final int SALIR = 4;

    public static EmpleadoDAO empleadoDAO = EmpleadoDAO.instanciaUnica();

    public static void menu() {


        while (true) {

            int option = ScannerUtils.capturarNumero("""
            \n===== MENÚ DE EMPLEADOS =====
            1. Registrar empleados
            2. Consultar empleados
            3. Exportar empleado a archivo
            4. Volver al menu principal""");

            switch (option) {

                case REGISTRAR_EMPLEADOS -> {
                    // Lógica para registrar empleados

                    try {
                        System.out.println("\n");
                        String nombre = ScannerUtils.capturarTexto("Nombre");
                        String documento = ScannerUtils.capturarRegistroDocumentoEmpleado("Documento");
                        String rol = ScannerUtils.capturarTexto("Rol");
                        String correo = ScannerUtils.capturarTexto("Correo");
                        Double salario = ScannerUtils.capturarDecimal("Salario");
                        Empleado empleado = new Empleado(nombre, documento, rol, correo, salario);

                        empleadoDAO.crear(empleado);
                        System.out.println("\nEmpleado creado satisfactoriamente");
                    } catch (SQLException e) {
                        System.err.println("No se ha podido registrar el empleado");
                    }
                }

                case CONSULTAR_EMPLEADOS -> {
                    // Lógica para consultar empleados

                    try {
                        String documento = ScannerUtils.capturarTexto("Documento");
                        Empleado empleado = empleadoDAO.empleadoPorDocumento(documento);

                        try {
                            empleado.mostrarEmpleado();
                        } catch (NullPointerException e) {
                            System.err.println("Empleado no encontrado por documento #" + documento);
                        }

                    } catch (SQLException e) {
                        System.err.println("No se ha encontrado el empleado");
                    }
                }

                case EXPORTAR -> {
                    try {
                        String documento = ScannerUtils.capturarTexto("Documento del empleado");
                        int id = empleadoDAO.consultarIdEmpleado(documento);

                        Empleado empleado = empleadoDAO.buscarPorId(id);

                        if (empleado != null) {
                            FileUtils.escribirEmpleado(empleado);
                            System.out.println("\nEmpleado exportado a archivo satisfactoriamente");
                        } else {
                            System.err.println("Empleado no encontrado");
                        }
                    } catch (SQLException e) {
                        System.err.println("Falla en la base de datos al exportar empleado");
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
