package com.apex;

import com.apex.util.ScannerUtils;
import com.apex.views.*;

public class Main {

    public static final int EMPLEADOS = 1;
    public static final int CLIENTES = 2;
    public static final int PRESTAMOS = 3;
    public static final int PAGOS = 4;
    public static final int REPORTES = 5;
    public static final int SALIR = 6;

    public static void main(String[] args) {
        while (true) {


            int option = ScannerUtils.capturarNumero("""
                    \n===== MENÚ PRINCIPAL =====
                    1. Empleados
                    2. Clientes
                    3. Préstamos
                    4. Pagos
                    5. Reportes
                    6. Salir del programa""");

            switch (option) {

                case EMPLEADOS -> EmpleadosView.menu();

                case CLIENTES -> ClientesView.menu();

                case PRESTAMOS -> PrestamosView.menu();

                case PAGOS -> PagosView.menu();

                case REPORTES -> ReportesView.menu();

                case SALIR -> System.exit(0);

                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
