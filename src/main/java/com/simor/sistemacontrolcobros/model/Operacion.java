package com.simor.sistemacontrolcobros.model;

import java.io.Serializable;

public class Operacion implements Serializable {
    private String nombre;
    private String operacion;
    private String media;
    private String descripcion;

    public Operacion() {
    }

    public Operacion(String nombre, String operacion, String media, String descripcion) {
        this.nombre = nombre;
        this.operacion = operacion;
        this.media = media;
        this.descripcion = descripcion;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
