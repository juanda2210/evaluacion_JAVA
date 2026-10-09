package com.apex.models;

import com.apex.util.ScannerUtils;

import java.sql.SQLException;
import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int cliente_id;
    private int empleado_id;
    private double monto;
    private double interes;
    private int cuotas;
    private LocalDate fecha_inicio;
    private String estado;

    public Prestamo() {
    }

    public Prestamo(int cliente_id, int empleado_id, double monto, double interes, int cuotas, LocalDate fecha_inicio) {
        this.cliente_id = cliente_id;
        this.empleado_id = empleado_id;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.fecha_inicio = fecha_inicio;
        this.estado = "Pendiente";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getEmpleado_id() {
        return empleado_id;
    }

    public void setEmpleado_id(int empleado_id) {
        this.empleado_id = empleado_id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void mostrarPrestamoConIdCliente() throws SQLException {
        Cliente clienteDelPrestamo = Cliente.consultar(this.cliente_id);

        if (clienteDelPrestamo != null) {
            System.out.println("\n Prestamo id " + this.id);
            System.out.println("-----------------------------------------------");
            System.out.println("Prestamo fecha " + fecha_inicio.getDayOfMonth() +
                    "/" + fecha_inicio.getMonth() +
                    "/" + fecha_inicio.getYear());
            System.out.println("----------------------------------------------");
            System.out.println("Cliente: " + clienteDelPrestamo.getNombre());
            System.out.println("Monto: " + this.monto);
            System.out.println("Interes total: " + this.interes);
            System.out.println("Periodo de tiempo: " + this.cuotas);
        }
    }

    public void calucularSimulacion() {
        double monto = ScannerUtils.capturarDecimal("Monto");
        int periodoTiempo = ScannerUtils.capturarNumero("Periodo de tiempo (meses)");

        Simulacion simulacion = new Simulacion(monto, periodoTiempo);
        simulacion.mostrarSimulacion();
    }

    public void cambiarEstado() {
        this.estado = "Pagado";
    }

    public void mostrarPrestamo() {
        System.out.println("\n--------Prestamo id " + this.id + "-----------");
        System.out.println("Id cliente " + this.cliente_id);
        System.out.println("--------------------------------");
        System.out.println("Monto: " + this.monto);
        System.out.println("Cuotas: " + this.cuotas);
        System.out.println("Interes generado: " + this.interes);
        System.out.println("-----------------------------------");
        System.out.println("Estado del prestamo: " + this.estado);
    }


}
