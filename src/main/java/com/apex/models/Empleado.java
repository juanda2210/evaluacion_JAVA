package com.apex.models;

public class Empleado {
    private int id;
    private String nombre;
    private String documento;
    private String rol;
    private String correo;
    private double salario;

    public Empleado() {
    }

    public Empleado(String nombre, String documento, String rol, String correo, double salario) {
        this.nombre = nombre;
        this.documento = documento;
        this.rol = rol;
        this.correo = correo;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public String getRol() {
        return rol;
    }

    public String getCorreo() {
        return correo;
    }

    public double getSalario() {
        return salario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void mostrarEmpleado() {
        System.out.println("-----------------");
        System.out.println("Nombre: " + this.nombre);
        System.out.println("id: " + this.id);
        System.out.println("Rol: " + this.rol);
        System.out.println("-----------------");
        System.out.println("SALARIO: " + this.salario);
    }
}
