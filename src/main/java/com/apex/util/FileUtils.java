package com.apex.util;

import com.apex.models.Cliente;
import com.apex.models.Empleado;
import com.apex.models.Pago;
import com.apex.models.Prestamo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static final String NOMBRE_ARCHIVO_CLIENTES = "clientes.txt";
    public static final String NOMBRE_ARCHIVO_EMPLEADOS = "empleados.txt";
    public static final String NOMBRE_ARCHIVO_PRESTAMOS = "prestamos.txt";
    public static final String NOMBRE_ARCHIVO_PAGOS = "pagos.txt";

    public static final String SEPARADOR = "|";

    public static void escribirEmpleado(Empleado empleado) {
        String linea = String.join(SEPARADOR,
                String.valueOf(empleado.getId()),
                empleado.getNombre(),
                empleado.getDocumento(),
                empleado.getRol(),
                empleado.getCorreo(),
                String.valueOf(empleado.getSalario())
        );

        String lineaFinal;

        lineaFinal = "EMPLEADO" + SEPARADOR + linea;

        try {
            Files.writeString(Paths.get(NOMBRE_ARCHIVO_EMPLEADOS),
                    lineaFinal + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error escribiendo el archivo. " + e.getMessage());
        }
    }

    public static void escribirCliente(Cliente cliente) {
        String linea = String.join(SEPARADOR,
                String.valueOf(cliente.getId()),
                cliente.getNombre(),
                cliente.getDocumento(),
                cliente.getCorreo(),
                cliente.getTelefono(),
                cliente.getSituacionCrediticia()
        );

        String lineaFinal;

        lineaFinal = "CLIENTE" + SEPARADOR + linea;

        try {
            Files.writeString(Paths.get(NOMBRE_ARCHIVO_CLIENTES),
                    lineaFinal + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error escribiendo el archivo. " + e.getMessage());
        }

    }

    public static void escribirPrestamo(Prestamo prestamo) {
        String linea = String.join(SEPARADOR,
                String.valueOf(prestamo.getId()),
                String.valueOf(prestamo.getCliente_id()),
                String.valueOf(prestamo.getEmpleado_id()),
                String.valueOf(prestamo.getMonto()),
                String.valueOf(prestamo.getInteres()),
                String.valueOf(prestamo.getCuotas()),
                prestamo.getFecha_inicio().toString(),
                prestamo.getEstado()
        );

        String lineaFinal;

        lineaFinal = "PRESTAMO" + SEPARADOR + linea;

        try {
            Files.writeString(Paths.get(NOMBRE_ARCHIVO_PRESTAMOS),
                    lineaFinal + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error escribiendo el archivo. " + e.getMessage());
        }

    }

    public static void escribirPago(Pago pago) {
        String linea = String.join(SEPARADOR,
                String.valueOf(pago.getId()),
                String.valueOf(pago.getPrestamo_id()),
                pago.getFecha_pago().toString(),
                String.valueOf(pago.getMonto())
        );

        String lineaFinal;

        lineaFinal = "PAGO" + SEPARADOR + linea;

        try {
            Files.writeString(Paths.get(NOMBRE_ARCHIVO_PAGOS),
                    lineaFinal + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Error escribiendo el archivo. " + e.getMessage());
        }

    }
}
