package com.apex.models;

import java.util.ArrayList;
import java.util.List;

public class GestorPagos {
    private List<Pago> pagos;

    private static final GestorPagos gestorPagos = new GestorPagos();

    public static GestorPagos instanciaUnica() {
        return gestorPagos;
    }

    public GestorPagos() {
        this.pagos = new ArrayList<>();
    }

    public void registrarPago(Pago pago) {
        pagos.add(pago);
    }

    public void listarPagos() {
        List<Pago> todosLosPagos = this.pagos;
        if (todosLosPagos.isEmpty()) {
            System.err.println("No hay pagos en la lista");
        } else {
            todosLosPagos.forEach(Pago::mostrarPago);
        }
    }

    public List<Pago> mostrarPagosMayoresA(double valor) {
        List<Pago> todosLosPagos = this.pagos;

        return todosLosPagos.stream()
                .filter(pago -> pago.monto > valor)
                .toList();
    }
}
