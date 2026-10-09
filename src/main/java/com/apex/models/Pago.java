package com.apex.models;


import java.time.LocalDate;

public class Pago {

    int id;
    int prestamo_id;
    LocalDate fecha_pago;
    double monto;



    public Pago() {
    }

    public Pago(int prestamo_id, LocalDate fecha_pago, double monto) {
        this.prestamo_id = prestamo_id;
        this.fecha_pago = fecha_pago;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrestamo_id() {
        return prestamo_id;
    }

    public void setPrestamo_id(int prestamo_id) {
        this.prestamo_id = prestamo_id;
    }

    public LocalDate getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(LocalDate fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void mostrarPago() {
        System.out.println("\n---PAGO ID " + this.id + "---");
        System.out.println("Fecha " + this.fecha_pago);
        System.out.println("----------");
        System.out.println("Del prestamo " + this.prestamo_id);
        System.out.println("Por un monto de " + this.monto);
    }

}
