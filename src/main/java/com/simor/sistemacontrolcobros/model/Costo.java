package com.simor.sistemacontrolcobros.model;

public class Costo {
    private int idCosto;
    private String materia;
    private Double costo;
    private String clienteEmpresa;
    private String encargado;
    private String atiendeYCobra;

    // Getters and Setters
    public int getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(int idCosto) {
        this.idCosto = idCosto;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getCliente() {
        return clienteEmpresa;
    }

    public void setCliente(String cliente) {
        this.clienteEmpresa = cliente;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getAtiendeYCobra() {
        return atiendeYCobra;
    }

    public void setAtiendeYCobra(String atiendeYCobra) {
        this.atiendeYCobra = atiendeYCobra;
    }

    public Costo() {
    }

    public Costo(String materia, Double costo, String clienteEmpresa, String encargado, String atiendeYCobra) {
        this.materia = materia;
        this.costo = costo;
        this.clienteEmpresa = clienteEmpresa;
        this.encargado = encargado;
        this.atiendeYCobra = atiendeYCobra;
    }

    public Costo(int idCosto, String materia, Double costo, String clienteEmpresa, String encargado, String atiendeYCobra) {
        this.idCosto = idCosto;
        this.materia = materia;
        this.costo = costo;
        this.clienteEmpresa = clienteEmpresa;
        this.encargado = encargado;
        this.atiendeYCobra = atiendeYCobra;
    }
}
