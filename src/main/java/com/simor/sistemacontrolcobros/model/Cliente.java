package com.simor.sistemacontrolcobros.model;

public class Cliente {
    private int idCliente;
    private String gestor;
    private String razonSocial;

    // Getters and Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getGestor() {
        return gestor;
    }

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Cliente(String gestor, String razonSocial) {
        this.gestor = gestor;
        this.razonSocial = razonSocial;
    }

    public Cliente(int idCliente, String gestor, String razonSocial) {
        this.idCliente = idCliente;
        this.gestor = gestor;
        this.razonSocial = razonSocial;
    }

    public Cliente() {
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", gestor='" + gestor + '\'' +
                ", razonSocial='" + razonSocial + '\'' +
                '}';
    }
}
