package com.simor.sistemacontrolcobros.model;

import java.sql.Date;

public class Verificacion {
    private int idVerificacion;
    private Vehiculo vehiculo;
    private Materia materia;
    private String verificentro;
    private Double precio;
    private Date fechaFolio;
    private String folio;

    public Verificacion(Materia materia, String verificentro, Double precio, Date fechaFolio, String folio) {
        this.materia = materia;
        this.verificentro = verificentro;
        this.precio = precio;
        this.fechaFolio = fechaFolio;
        this.folio = folio;
    }

    public Verificacion(Vehiculo vehiculo, Materia materia, String verificentro, Double precio, Date fechaFolio, String folio) {
        this.vehiculo = vehiculo;
        this.materia = materia;
        this.verificentro = verificentro;
        this.precio = precio;
        this.fechaFolio = fechaFolio;
        this.folio = folio;
    }

    public enum Materia {
        HUMO, MOTRIZ, ARRASTRE, GASOLINA, NULL
    }

    // Getters and Setters
    public int getIdVerificacion() {
        return idVerificacion;
    }

    public void setIdVerificacion(int idVerificacion) {
        this.idVerificacion = idVerificacion;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getVerificentro() {
        return verificentro;
    }

    public void setVerificentro(String verificentro) {
        this.verificentro = verificentro;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Date getFechaFolio() {
        return fechaFolio;
    }

    public void setFechaFolio(Date fechaFolio) {
        this.fechaFolio = fechaFolio;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Verificacion() {
    }

    public Verificacion(int idVerificacion, Vehiculo vehiculo, Materia materia, String verificentro, Double precio, Date fechaFolio, String folio) {
        this.idVerificacion = idVerificacion;
        this.vehiculo = vehiculo;
        this.materia = materia;
        this.verificentro = verificentro;
        this.precio = precio;
        this.fechaFolio = fechaFolio;
        this.folio = folio;
    }

    public void setMateria(String materia) {
        switch (materia) {
            case "HUMO":
                this.materia = Materia.HUMO;
                break;
            case "ARRASTRE":
                this.materia = Materia.ARRASTRE;
                break;
            case "MOTRIZ":
                this.materia = Materia.MOTRIZ;
                break;
            case "GASOLINA":
                this.materia = Materia.GASOLINA;
                break;
            default:
                this.materia = Materia.NULL;
                break;
        }
    }

    @Override
    public String toString() {
        return "Verificacion{" +
                "idVerificacion=" + idVerificacion +
                ", vehiculo=" + vehiculo.toString() +
                ", materia=" + materia +
                ", verificentro='" + verificentro + '\'' +
                ", precio=" + precio +
                ", fechaFolio=" + fechaFolio +
                ", folio='" + folio + '\'' +
                '}';
    }
}
