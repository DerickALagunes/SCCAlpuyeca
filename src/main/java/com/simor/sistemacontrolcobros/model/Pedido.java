package com.simor.sistemacontrolcobros.model;

import java.sql.Date;

public class Pedido {
    private int idPedido;
    private Date fechaEnvio;
    private String numeroGuia;
    private String recibio;
    private String foto;

    // Getters and Setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getNumeroGuia() {
        return numeroGuia;
    }

    public void setNumeroGuia(String numeroGuia) {
        this.numeroGuia = numeroGuia;
    }

    public String getRecibio() {
        return recibio;
    }

    public void setRecibio(String recibio) {
        this.recibio = recibio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Pedido(Date fechaEnvio, String numeroGuia, String recibio, String foto) {
        this.fechaEnvio = fechaEnvio;
        this.numeroGuia = numeroGuia;
        this.recibio = recibio;
        this.foto = foto;
    }

    public Pedido(int idPedido, Date fechaEnvio, String numeroGuia, String recibio, String foto) {
        this.idPedido = idPedido;
        this.fechaEnvio = fechaEnvio;
        this.numeroGuia = numeroGuia;
        this.recibio = recibio;
        this.foto = foto;
    }

    public Pedido() {
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", fechaEnvio=" + fechaEnvio +
                ", numeroGuia='" + numeroGuia + '\'' +
                ", recibio='" + recibio + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}

