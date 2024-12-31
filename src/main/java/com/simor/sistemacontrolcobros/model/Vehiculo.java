package com.simor.sistemacontrolcobros.model;

public class Vehiculo {
    private int idVehiculo;
    private String placa;
    private String serie;
    private Cliente cliente;

    public Vehiculo(String placa, String serie, Cliente cliente) {
        this.placa = placa;
        this.serie = serie;
        this.cliente = cliente;
    }

    // Getters and Setters
    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo(String placa, String serie) {
        this.placa = placa;
        this.serie = serie;
    }

    public Vehiculo() {
    }

    public Vehiculo(int idVehiculo, String placa, String serie, Cliente cliente) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.serie = serie;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo=" + idVehiculo +
                ", placa='" + placa + '\'' +
                ", serie='" + serie + '\'' +
                ", cliente=" + cliente.toString() +
                '}';
    }
}
