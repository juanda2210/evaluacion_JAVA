package com.apex.util;

import com.apex.dao.ClienteDAO;
import com.apex.dao.EmpleadoDAO;
import com.apex.models.Cliente;
import com.apex.models.Empleado;

import java.sql.SQLException;
import java.util.Scanner;

public class ScannerUtils {

    public static final Scanner SCANNER = new Scanner(System.in);

    public static String capturarTexto(String mensaje) {
        String dato;

        while (true) {
            System.out.println(mensaje + ": ");
            dato = SCANNER.nextLine();

            if (!dato.isBlank()) {
                return dato;
            }

            System.err.println("Error: el dato ingresado no puede estar vacio");
        }
    }

    public static int capturarNumero(String mensaje) {
        System.out.println(mensaje + ": ");

        while (!SCANNER.hasNextInt()) {
            System.out.println("Dato no aceptado " + mensaje + ": ");
            SCANNER.next();
        }

        int dato = SCANNER.nextInt();
        SCANNER.nextLine();
        return dato;
    }

    public static double capturarDecimal(String mensaje) {
        System.out.println(mensaje + ": ");

        while (!SCANNER.hasNextDouble()) {
            System.out.println("Dato no aceptado " + mensaje + ": ");
            SCANNER.next();
        }

        double dato = SCANNER.nextDouble();
        SCANNER.nextLine();
        return dato;
    }

    public static String capturarRegistroDocumentoCliente(String mensaje) throws SQLException {
        String dato;

        while (true) {
            System.out.println(mensaje + ": ");
            dato = SCANNER.nextLine();

            if (dato.isBlank()) {
                System.err.println("Error: el dato ingresado no puede estar vacío");
                continue;
            }

            if (!dato.matches("\\d+")) {
                System.err.println("Error: el documento solo puede contener números");
                continue;
            }

            ClienteDAO clienteDAO = ClienteDAO.instanciaUnica();
            Cliente cliente = clienteDAO.clientePorDocumento(dato);

            if (cliente != null) {
                System.err.println("Error: el documento ya está asignado a otro cliente");
                continue;
            }

            return dato;
        }
    }

    public static String capturarRegistroDocumentoEmpleado(String mensaje) throws SQLException {
        String dato;

        while (true) {
            System.out.println(mensaje + ": ");
            dato = SCANNER.nextLine();

            if (dato.isBlank()) {
                System.err.println("Error: el dato ingresado no puede estar vacío");
                continue;
            }

            if (!dato.matches("\\d+")) {
                System.err.println("Error: el documento solo puede contener números");
                continue;
            }

            EmpleadoDAO empleadoDAO = EmpleadoDAO.instanciaUnica();
            Empleado empleado = empleadoDAO.empleadoPorDocumento(dato);

            if (empleado != null) {
                System.err.println("Error: el documento ya está asignado a otro empleado");
                continue;
            }

            return dato;
        }
    }
}
