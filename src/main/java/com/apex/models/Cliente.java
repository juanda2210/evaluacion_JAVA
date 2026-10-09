package com.apex.models;

import com.apex.dao.ClienteDAO;

import java.sql.SQLException;
import java.util.List;

public class Cliente {
    private int id;
    private String nombre;
    private String documento;
    private String correo;
    private String telefono;
    private String situacionCrediticia;

    public static ClienteDAO clienteDAO = ClienteDAO.instanciaUnica();

    public Cliente() {
    }

    public Cliente(String nombre, String documento, String correo, String telefono) {
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
        this.telefono = telefono;
        this.situacionCrediticia = "Puntual";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSituacionCrediticia() {
        return situacionCrediticia;
    }

    public void setSituacionCrediticia(String situacionCrediticia) {
        this.situacionCrediticia = situacionCrediticia;
    }

    public static Cliente consultar (int id) throws SQLException {
        List<Cliente> clientes = clienteDAO.listar();

        Cliente clientePorBuscar = clientes.stream()
                .filter(cliente -> cliente.getId() == id)
                .findFirst()
                .orElse(null);

        if (clientePorBuscar != null) {
            return clientePorBuscar;
        }

        throw new SQLException("Cliente no encontrado por el id " + id);
    }

    public void mostrarCliente() {
        System.out.println("\n---------------------------");
        System.out.println("id: " + this.id);
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Documento: " + this.documento);
        System.out.println("Correo: " + this.correo);
        System.out.println("Telefono: " + this.telefono);
    }

}
