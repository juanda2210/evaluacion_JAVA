package com.apex.models;

public class Simulacion {
    private double monto;
    private double montoMensual;
    private double interesAnual;
    private double interesMensual;
    private double cuotaMensual;
    private double tasaInteresAnual;
    private double tasaInteresMensual;
    private int periodoDeMeses;
    private double totalAPagar;

    static final double TASAFIJAANUAL = 0.195;

    public Simulacion(double monto, int periodoDeMeses) {
        this.monto = monto;
        this.periodoDeMeses = periodoDeMeses;
        this.montoMensual = this.monto / this.periodoDeMeses;
        this.tasaInteresAnual = TASAFIJAANUAL;
        this.tasaInteresMensual = this.tasaInteresAnual / 12;
        this.interesAnual = this.monto * tasaInteresAnual;
        this.interesMensual = this.monto * tasaInteresMensual;
    }

    public double getMonto() {
        return monto;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public double getInteresAnual() {
        return interesAnual;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public double getInteresMensual() {
        return interesMensual;
    }

    public double getTasaInteresAnual() {
        return tasaInteresAnual;
    }

    public double getTasaInteresMensual() {
        return tasaInteresMensual;
    }

    public int getPeriodoDeMeses() {
        return periodoDeMeses;
    }

    public double getTotalAPagar() {
        return totalAPagar;
    }

    private void calcularCuotaMensual() {
        this.cuotaMensual = this.montoMensual + this.interesMensual;
    }

    private void calcularTotalAPagar() {
        this.totalAPagar = this.monto + this.interesAnual;
    }

    public void mostrarSimulacion() {
        this.calcularCuotaMensual();
        this.calcularTotalAPagar();

        System.out.println("\nSimulacion de credito");
        System.out.println("---------------------");
        System.out.println("\nMonto a prestar: " + this.monto);
        System.out.println("Cuota mensual: " + this.cuotaMensual);
        System.out.println("Total a pagar luego de " + this.periodoDeMeses  + " meses: " + this.totalAPagar);
        System.out.println("-----------------------------------------------");
        System.out.println("\nINTERESES");
        System.out.println("-----------");
        System.out.println("Tasa de interés mensual: " + this.tasaInteresMensual * 100 + "%");
        System.out.println("Interes mensual: " + this.interesMensual);
        System.out.println("Tasa de interés anual: " + this.tasaInteresAnual * 100 + "%");
        System.out.println("Interes anual: " + this.interesAnual);
    }

}
